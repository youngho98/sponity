package com.ssafy.sponity.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.sponity.jwt.JWTUtil;
import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.service.ClubManagerService;
import com.ssafy.sponity.model.service.S3Service;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/club-manager")
public class ClubManagerController {
	
	// DI
	private final ClubManagerService clubManagerService;
	private final JWTUtil jwtUtil;
	private final S3Service s3Service;
	public ClubManagerController(ClubManagerService clubManagerService, JWTUtil jwtUtil, S3Service s3Service) {
		this.clubManagerService = clubManagerService;
		this.jwtUtil = jwtUtil;
		this.s3Service = s3Service;
	}
	
	
	// 로그인 사용자의 ID 추출하기
	// - 모임장 여부 판단을 위해 userId가 필요하므로, 요청 헤더에 담긴 JWT에서 현재 로그인 중인 사용자의 ID를 추출합니다.
	public String getLoginId(HttpServletRequest request) {
		String token = request.getHeader("Authorization").split(" ")[1];
    	return jwtUtil.getUserId(token);
	}
	
	
	// 모임 생성
    @PostMapping("")
    public ResponseEntity<String> makeClub(@RequestBody Club club, HttpServletRequest request) {
    	int result = clubManagerService.makeClub(club, getLoginId(request));
    	
    	if (result > 0) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
    
	// 모임 수정
    @PutMapping("/{clubId}")
    public ResponseEntity<Integer> modifyClub(@RequestBody Club club, HttpServletRequest request) {
    	int result = clubManagerService.modifyClub(club, getLoginId(request));
    	
    	/*
    	 * 반환하는 숫자의 의미
    	 * 1: 모임장이 아님
    	 * 2: 모임 정보 수정 완료
    	 * 3: 기타 서버 오류
    	 */
    	switch(result) {
    	case 1: return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
    	case 2: return new ResponseEntity<>(2, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	// 모임 삭제
	@DeleteMapping("/{clubId}")
    public ResponseEntity<Integer> removeClub(@PathVariable("clubId") int clubId, HttpServletRequest request) {
    	int result = clubManagerService.removeClub(clubId, getLoginId(request));
    	
    	/*
    	 * 반환하는 숫자의 의미
    	 * 1: 모임장이 아님
    	 * 2: 모임 삭제 완료
    	 * 3: 기타 서버 오류
    	 */
    	switch(result) {
    	case 1: return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
    	case 2: return new ResponseEntity<>(2, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	
	// 모임장 권한 이전
	@PatchMapping("/{clubId}/member-list/{userId}/leader")
	public ResponseEntity<?> leaderChange(@PathVariable("clubId") int clubId, @PathVariable("userId") String newLeaderId) {
		Map<String, Object> map = new HashMap<>();
		map.put("clubId", clubId);
		map.put("newLeaderId", newLeaderId);
		
		int result = clubManagerService.leaderChange(map);
		
    	if (result > 0) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 회원 강퇴
	@DeleteMapping("/{clubId}/member-list/{userId}")
	public ResponseEntity<?> expelMember(@PathVariable("clubId") int clubId, @PathVariable("userId") String expelId) {
		Map<String, Object> map = new HashMap<>();
		map.put("clubId", clubId);
		map.put("expelId", expelId);
		
		int result = clubManagerService.expelMember(map);
		
    	if (result > 0) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// ----- 클럽 이미지 기능 ----------------------------------------------------------------------------------------
	
	
	// 클럽 이미지 조회
	@GetMapping("/{clubId}/profile-img")
	public ResponseEntity<String> getClubPicture(@PathVariable("clubId") int clubId) {
		String url = clubManagerService.getClubPicture(clubId);
		
		if (url != null) {
			return new ResponseEntity<>(url, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	// 클럽 이미지 추가
	@PostMapping("/{clubId}/profile-img")
	public ResponseEntity<?> uploadClubPicture(@RequestParam("img") MultipartFile file, @PathVariable("clubId") int clubId) throws IOException {
		String url = s3Service.uploadClubPicture(file, clubId);
		
		if (url != null) {
			return new ResponseEntity<>(url, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	// 클럽 이미지 삭제
	// deleteClubPicture
	@DeleteMapping("/{clubId}/profile-img/{file-name}")
	public void deleteClubPicture(@PathVariable("file-name") String fileName, @PathVariable("clubId") int clubId) {
		s3Service.deleteClubPicture(fileName, clubId);
	}
	
}
