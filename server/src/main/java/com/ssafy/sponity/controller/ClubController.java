package com.ssafy.sponity.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sponity.jwt.JWTUtil;
import com.ssafy.sponity.model.dto.Board;
import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.dto.User;
import com.ssafy.sponity.model.service.ClubService;
import com.ssafy.sponity.model.service.S3Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/club")
public class ClubController {
	
	// DI
	private final ClubService clubService;
	private final JWTUtil jwtUtil;
	private final S3Service s3Service;
	public ClubController (ClubService clubService, JWTUtil jwtUtil, S3Service s3Service) {
		this.clubService = clubService;
		this.jwtUtil = jwtUtil;
		this.s3Service = s3Service;
	}
	
	
	// 도메인에서 clubId를, request에서 로그인 사용자의 ID를 추출해 map으로 반환하기
	public Map<String, Object> getIdMap(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		String token = request.getHeader("Authorization").split(" ")[1];
		String userId = jwtUtil.getUserId(token);
		
		Map<String, Object> idMap = new HashMap<>();
		idMap.put("userId", userId);
		idMap.put("clubId", clubId);
		
    	return idMap;
	}
	
	
	// 클럽 검색
	@PostMapping("/search")
	public ResponseEntity<List<Club>> searchClub(@RequestBody SearchDTO searchDTO) {
		Map<String, String> map = new HashMap<>();
		map.put("category", searchDTO.getCategory());
		map.put("wideArea", searchDTO.getWideArea());
		map.put("detailArea", searchDTO.getDetailArea());
		map.put("keyword", searchDTO.getKeyword());
		
		List<Club> result = clubService.searchClub(map);
		
		if (result != null) {
			// RestController에서 리스트 반환시, 자동으로 JSON으로 변환됨
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	// 클럽 상세조회 
	@GetMapping("/{clubId}")
	public ResponseEntity<ClubDetailDTO> detailClub(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		Map<String, Object> idMap = getIdMap(clubId, request);
		
		Club club = clubService.detailClub(clubId);
		
		/*
		 * 해당 클럽에 대한 사용자의 지위
		 * 1: 미가입자 (member 테이블에 포함 X)
		 * 2: 일반 멤버 (leader: 'N')
		 * 3: 모임장 (leader: 'Y')
		 */
		int userStatus = clubService.userStatus(idMap);
		
		/*
		 * 해당 클럽에 대한 사용자의 좋아요 여부
		 * 0: 좋아요 X
		 * 1: 좋아요 O
		 */
		int isLike = clubService.isLike(idMap);
		
		// 기존 Club DTO에 userStatus,isLike을 추가한 DTO
		ClubDetailDTO clubDetailDTO = new ClubDetailDTO(club.getClubId(), club.getClubName(), club.getCategory(), 
				club.getWideArea(), club.getDetailArea(), club.getIntroduction(), club.getClubImg(), 
				club.getMemberNum(), club.getLikeNum(), 
				userStatus, isLike);
		
		return new ResponseEntity<>(clubDetailDTO, HttpStatus.OK);
	}
	
	
	// 모임 회원 조회
	@GetMapping("/{clubId}/member-list")
	public ResponseEntity<List<User>> searchMember(@PathVariable("clubId") int clubId) {
		List<User> userList = clubService.searchMember(clubId);
		
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
	
	// 클럽 가입
	@PostMapping("/{clubId}")
	public ResponseEntity<Integer> clubIn(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		Map<String, Object> idMap = getIdMap(clubId, request);
		
		int result = clubService.clubIn(idMap);
		
		/*
		 * 반환하는 숫자의 의미
		 * 1: 이미 가입된 회원
		 * 2: 클럽 가입 완료
		 * 3: 기타 서버 오류
		 */
		switch (result) {
		case 1: return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
		case 2: return new ResponseEntity<>(2, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(3, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 클럽 탈퇴
	@DeleteMapping("/{clubId}")
	public ResponseEntity<Integer> clubOut(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		Map<String, Object> idMap = getIdMap(clubId, request);
		
		int result = clubService.clubOut(idMap);
		
		/*
		 * 반환하는 숫자의 의미
		 * 1: 가입된 회원이 아님
		 * 2: 클럽 탈퇴 완료
		 * 3: 기타 서버 오류
		 */
		switch (result) {
		case 1: return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
		case 2: return new ResponseEntity<>(2, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(3, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@Data
	public static class SearchDTO {
		private String wideArea;
		private String detailArea;
		private String category;
		private String keyword;
	}
	
	@Data
	@AllArgsConstructor
	public static class ClubDetailDTO {
	    private int clubId;
	    private String clubName;
	    private String category;
	    private String wideArea;
	    private String detailArea;
	    private String introduction;
	    private String clubImg;
	    private int memberNum;
	    private int likeNum;
		private int userStatus;
		private int isLike;
	}
	
	
	// ----- 클럽 좋아요 기능 -------------------------------------------------------------------------------------------------------
	
	
	// 클럽 좋아요
	@PostMapping("/{clubId}/like")
	public ResponseEntity<?> clubLike(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		Map<String, Object> idMap = getIdMap(clubId, request);
		
		int result = clubService.clubLike(idMap);
		
		if(result > 0) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	
	// 클럽 좋아요 취소
	@DeleteMapping("/{clubId}/like")
	public ResponseEntity<?> clubDislike(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		Map<String, Object> idMap = getIdMap(clubId, request);
		
		int result = clubService.clubDislike(idMap);
		
		if(result > 0) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		

	// ----- 클럽 게시판 기능 -------------------------------------------------------------------------------------------------------

	
	// 클럽 내 게시판 조회
	@GetMapping("/{clubId}/board")
	public ResponseEntity<List<Board>> boardList(@PathVariable("clubId") int clubId) {
		List<Board> boardList = clubService.boardList(clubId);
		
		if (boardList != null) {
			return new ResponseEntity<>(boardList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	// 게시글 작성
	@PostMapping("/{clubId}/board")
	public ResponseEntity<Integer> createBoard(
			@PathVariable("clubId") int clubId, HttpServletRequest request,
			@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("img1") MultipartFile img1, @RequestParam("img2") MultipartFile img2, @RequestParam("img3") MultipartFile img3) throws IOException {

		/*
		 * 반환하는 숫자의 의미
		 * 1: 제목이 비어 있음
		 * 2: 내용이 비어 있음
		 * 3: 이미지 관련 s3 서버 오류
		 * 4: 기타 내부 서버 오류
		 * 5: 게시글 작성 성공
		 */
		
		Board board = new Board();
		
		// 제목, 내용이 비어있는 경우 에러 반환
		// - 정보가 있다면 board 객체에 세팅
		if (title == null || title.trim().isEmpty()) {
			return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
		} else {
			board.setTitle(title);
		}
		if (content == null || content.trim().isEmpty()) {
			return new ResponseEntity<>(2, HttpStatus.BAD_REQUEST);
		} else {
			board.setContent(content);
		}
		
		
		// board 객체에 clubId, userId 값 세팅
		board.setClubId(clubId);
		String token = request.getHeader("Authorization").split(" ")[1];
		String userId = jwtUtil.getUserId(token);
		board.setUserId(userId);
		
		
		// 첨부한 이미지를 s3 서버에 업로드
		String folderName = "board-picture";
		
		if (img1 != null && !img1.isEmpty()) {
			String url1 = s3Service.upload(folderName, img1, userId);
			if (url1 == null) {
				return new ResponseEntity<>(3, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				board.setImg1(url1);
			}
		}
		if (img2 != null && !img2.isEmpty()) {
			String url2 = s3Service.upload(folderName, img2, userId);
			if (url2 == null) {
				return new ResponseEntity<>(3, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				board.setImg2(url2);
			}
		}
		if (img3 != null && !img3.isEmpty()) {
			String url3 = s3Service.upload(folderName, img3, userId);			
			if (url3 == null) {
				return new ResponseEntity<>(3, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				board.setImg3(url3);
			}
		}
		
		
		int result = clubService.createBoard(board);
		
		if (result > 0) {
			return new ResponseEntity<>(5, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(4, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 게시글 조회
//	@Mapping("/{clubId}/board/{boardId}")
//	public ResponseEntity<Board> getBoard(@PathVariable("clubId") int clubId, HttpServletRequest request) {
//		
//	}	
	
	
	// 게시글 수정
//	@Mapping("/{clubId}/board/{boardId}")
//	public ResponseEntity<?> modifyBoard(@PathVariable("clubId") int clubId, HttpServletRequest request) {
//		
//	}
	
	
	// 게시글 삭제
//	@Mapping("/{clubId}/board/{boardId}")
//	public ResponseEntity<?> removeBoard(@PathVariable("clubId") int clubId, HttpServletRequest request) {
//		
//	}
	
	
	// ----- 클럽 게시판 내 댓글 기능 -------------------------------------------------------------------------------------------------------
	
	
	// 댓글 작성
//	@Mapping("/{clubId}/board/{boardId}")
//	public ResponseEntity<> (@PathVariable("clubId") int clubId, HttpServletRequest request) {
//		
//	}
	
	
	// 댓글 수정
//	@Mapping("/{clubId}/board/{boardId}/{reviewId}")
//	public ResponseEntity<> (@PathVariable("clubId") int clubId, HttpServletRequest request) {
//		
//	}
	
	
	// 댓글 삭제
//	@Mapping("/{clubId}/board/{boardId}/{reviewId}")
//	public ResponseEntity<> (@PathVariable("clubId") int clubId, HttpServletRequest request) {
//		
//	}
	
	
}
