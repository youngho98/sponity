package com.ssafy.sponity.controller;

import java.io.IOException;

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
import com.ssafy.sponity.model.dto.User;
import com.ssafy.sponity.model.service.MypageService;
import com.ssafy.sponity.model.service.S3Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@RestController
@RequestMapping("/my-page")
public class MypageController {
	
	// DI
	private final MypageService mypageService;
	private final JWTUtil jwtUtil;
	private final S3Service s3Service;
	public MypageController(MypageService mypageService, JWTUtil jwtUtil, S3Service s3Service) {
		this.mypageService = mypageService;
		this.jwtUtil = jwtUtil;
		this.s3Service = s3Service;
	}
	
	
	// 마이페이지 
	// - 요청 헤더에 저장된 JWT에서 userId를 추출해, 이를 기반으로 내 정보 조회
	@GetMapping("")
	public ResponseEntity<User> searchMyInfo(HttpServletRequest request) {
		String token = request.getHeader("Authorization").split(" ")[1];
		User user = mypageService.searchMyInfo(jwtUtil.getUserId(token));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	
	// 회원정보 수정
	@PutMapping("/modify-profile")
	public ResponseEntity<Integer> modifyProfile(@RequestBody User user, HttpServletRequest request) {
		String token = request.getHeader("Authorization").split(" ")[1];
		String curNickname = jwtUtil.getNickname(token);
		String curEmail = jwtUtil.getEmail(token);
		
		int result = mypageService.modifyProfile(user, curNickname, curEmail);
		
		/*
		 * 반환하는 숫자의 의미
		 * 1: 닉네임 중복
		 * 2: 이메일 중복
		 * 3: 회원정보 수정 완료
		 * 4: 기타 서버 오류
		 */
		switch (result) {
		case 1: return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
		case 2: return new ResponseEntity<>(2, HttpStatus.BAD_REQUEST);
		case 3: return new ResponseEntity<>(3, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(4, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 비밀번호 변경
	@PatchMapping("/modify-pw")
	public ResponseEntity<Integer> modifyPw(@RequestBody PwDTO pwDTO, HttpServletRequest request) {
		String curPw = pwDTO.getCurPw();
		String newPw = pwDTO.getNewPw();
		
		String token = request.getHeader("Authorization").split(" ")[1];
		String userId = jwtUtil.getUserId(token);
		
		int result = mypageService.modifyPw(curPw, newPw, userId);
		
		/*
		 * 반환하는 숫자의 의미
		 * 1: 비밀번호 틀림
		 * 2: 새로운 비밀번호가 비밀번호 규칙에 위반됨
		 * 3: 비밀번호 변경 완료
		 * 4: 기타 서버 오류
		 */
		switch (result) {
		case 1: return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
		case 2: return new ResponseEntity<>(2, HttpStatus.BAD_REQUEST);
		case 3: return new ResponseEntity<>(3, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(4, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 회원 탈퇴
	@PatchMapping("/withdraw")
	public ResponseEntity<Integer> withdraw(@RequestBody PwDTO pwDTO, HttpServletRequest request) {
		String password = pwDTO.getCurPw();
		
		String token = request.getHeader("Authorization").split(" ")[1];
		String userId = jwtUtil.getUserId(token);
		
		int result = mypageService.withdraw(password, userId);
		
		/*
		 * 반환하는 숫자의 의미
		 * 1: 비밀번호 틀림
		 * 2: 회원탈퇴 완료
		 * 3: 기타 서버 오류
		 */
		switch (result) {
		case 1: return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
		case 2: return new ResponseEntity<>(2, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(3, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 프로필 이미지 업로드
	@PostMapping("/profile-img")
	public ResponseEntity<String> upload (@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
		String token = request.getHeader("Authorization").split(" ")[1];
		String userId = jwtUtil.getUserId(token);

		String url = s3Service.upload(file, userId);
		
		if (url != null) {
			return new ResponseEntity<>(url, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	// 프로필 이미지 삭제
	@DeleteMapping("/profile-img/{file-name}")
	public void delete (@PathVariable("file-name") String fileName, HttpServletRequest request) {
		String token = request.getHeader("Authorization").split(" ")[1];
		String userId = jwtUtil.getUserId(token);
		
		s3Service.delete(fileName, userId);
	}
	
	
	@Data
	public static class PwDTO {
	    private String curPw;
	    private String newPw;
	}
	
}
