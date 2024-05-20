package com.ssafy.sponity.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sponity.jwt.JWTUtil;
import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.service.ClubService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/club")
public class ClubController {
	
	// DI
	private final ClubService clubService;
	private final JWTUtil jwtUtil;
	public ClubController (ClubService clubService, JWTUtil jwtUtil) {
		this.clubService = clubService;
		this.jwtUtil = jwtUtil;
	}
	
	
	// 로그인 사용자의 ID 추출하기
	public String getLoginId(HttpServletRequest request) {
		String token = request.getHeader("Authorization").split(" ")[1];
    	return jwtUtil.getUserId(token);
	}
	
	
	// 모임 검색
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
	
	
	// 모임 상세조회 
	@GetMapping("/{clubId}")
	public ResponseEntity<ClubDetailDTO> detailClub(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", getLoginId(request));
		map.put("clubId", clubId);
		
		Club club = clubService.detailClub(clubId);
		
		/*
		 * 해당 모임에 대한 사용자의 지위
		 * 1: 미가입자 (member 테이블에 포함 X)
		 * 2: 일반 멤버 (leader: 'N')
		 * 3: 모임장 (leader: 'Y')
		 */
		int userStatus = clubService.userStatus(map);
		
		/*
		 * 해당 모임에 대한 사용자의 좋아요 여부
		 * 0: 좋아요 X
		 * 1: 좋아요 O
		 */
		int isLike = clubService.isLike(map);
		
		// 기존 Club DTO에 userStatus,isLike을 추가한 DTO
		ClubDetailDTO clubDetailDTO = new ClubDetailDTO(club.getClubId(), club.getClubName(), club.getCategory(), 
				club.getWideArea(), club.getDetailArea(), club.getIntroduction(), club.getClubImg(), 
				club.getMemberNum(), club.getLikeNum(), 
				userStatus, isLike);
		
		return new ResponseEntity<>(clubDetailDTO, HttpStatus.OK);
	}
	
	
	// 모임 가입
	@PostMapping("/{clubId}")
	public ResponseEntity<Integer> clubIn(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", getLoginId(request));
		map.put("clubId", clubId);
		
		int result = clubService.clubIn(map);
		
		/*
		 * 반환하는 숫자의 의미
		 * 1: 이미 가입된 회원
		 * 2: 모임 가입 완료
		 * 3: 기타 서버 오류
		 */
		switch (result) {
		case 1: return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
		case 2: return new ResponseEntity<>(2, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(3, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 모임 탈퇴
	@DeleteMapping("/{clubId}")
	public ResponseEntity<Integer> clubOut(@PathVariable("clubId") int clubId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", getLoginId(request));
		map.put("clubId", clubId);
		
		int result = clubService.clubOut(map);
		
		/*
		 * 반환하는 숫자의 의미
		 * 1: 가입된 회원이 아님
		 * 2: 모임 탈퇴 완료
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
}
