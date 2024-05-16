package com.ssafy.sponity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sponity.model.dto.User;
import com.ssafy.sponity.model.service.MypageService;

@RestController
@RequestMapping("/my-page")
public class MypageController {
	
	// DI
	private MypageService mypageService;
	public MypageController(MypageService mypageService) {
		this.mypageService = mypageService;
	}
	
	
	// 마이페이지 
	// - 클라이언트로부터 JWT에 저장된 닉네임을 받아, 이를 기반으로 내 정보 조회
	@PostMapping("/")
	public ResponseEntity<User> searchMyInfo(@RequestBody User user) {
		User result = mypageService.searchMyInfo(user.getNickname());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
//	// 회원정보 수정
//	@PutMapping("/modify-profile")
//	public ResponseEntity<String> modifyProfile(@RequestBody User user) {
//		int result = mypageService.modifyProfile(user);
//		
//    	if (result > 0) {
//    		return new ResponseEntity<>("회원정보 수정 완료", HttpStatus.OK);
//    	} else {
//    		return new ResponseEntity<>("닉네임 중복", HttpStatus.BAD_REQUEST);
//    	}
//	}
//	
//	
//	// 비밀번호 변경
//	// - 비밀번호 확인 후, 비밀번호 규칙에 맞는 경우만 갱신
//	@PatchMapping("/modify-pw")
//	public ResponseEntity<String> modifyPassword(@RequestBody User user) {
//		int result = mypageService.modifyProfile(user);
//		
//		/*
//		 * 반환하는 숫자의 의미
//		 * 1: 비밀번호 틀림
//		 * 2: 새로운 비밀번호가 비밀번호 규칙에 위반됨
//		 * 3: 비밀번호 변경 완료
//		 * 4: 기타 서버 오류
//		 */
//		switch (result) {
//		case 1: return new ResponseEntity<>("비밀번호 틀림", HttpStatus.BAD_REQUEST);
//		case 2: return new ResponseEntity<>("비밀번호 규칙 위반", HttpStatus.BAD_REQUEST);
//		case 3: return new ResponseEntity<>("비밀번호 변경 완료", HttpStatus.OK);
//		}
//		
//		return new ResponseEntity<>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
//	
//	// 회원탈퇴
//	// - 비밀번호 확인 후, delete 컬럼 값을 Y로 갱신 및 로그아웃
//	@DeleteMapping("/withdraw")
//	public ResponseEntity<String> withdraw(@RequestBody User user) {
//		int result = mypageService.withdraw(user);
//		
//    	if (result > 0) {
//    		return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
//    	} else {
//    		return new ResponseEntity<>("비밀번호 틀림", HttpStatus.BAD_REQUEST);
//    	}
//	}
}
