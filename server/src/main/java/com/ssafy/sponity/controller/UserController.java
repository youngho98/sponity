package com.ssafy.sponity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sponity.model.dto.User;
import com.ssafy.sponity.model.service.UserService;

@RestController
public class UserController {
	
	// DI
	private UserService userService;
	public UserController (UserService userService) {
		this.userService = userService;
	}
	
	
    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Integer> join(@RequestBody User user) {
    	
        int result = userService.join(user);
        
        /*
         * 반환하는 숫자의 의미
         * 1: userId 중복
         * 2: nickname 중복
         * 3: email 중복
         * 4: ID 규칙 위반
         * 5: 비밀번호 규칙 위반
         * 6: 회원가입 완료
         * 7: 기타 서버 오류
         */
        switch(result) {
        case 1: return new ResponseEntity<>(1, HttpStatus.CONFLICT);
        case 2: return new ResponseEntity<>(2, HttpStatus.CONFLICT);
        case 3: return new ResponseEntity<>(3, HttpStatus.CONFLICT);
        
        case 4: return new ResponseEntity<>(4, HttpStatus.BAD_REQUEST);
        case 5: return new ResponseEntity<>(5, HttpStatus.BAD_REQUEST);

        case 6: return new ResponseEntity<>(6, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(7, HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
    
    
    // 아이디 찾기
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody User user) {

    	String id = userService.findId(user.getUserName(), user.getEmail());
    	
    	if (id != null) {
    		return new ResponseEntity<>(id, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>("찾는 아이디가 없습니다.", HttpStatus.NOT_FOUND);
    	}
    	
    }
    
    
    // 비밀번호 재설정
    // - 이메일로 임시 비밀번호 전송
    @PostMapping("/reset-pw")
    public ResponseEntity<Integer> resetPassword(@RequestBody User user) {

    	int result = userService.resetPassword(user.getUserId(), user.getEmail());
    	
    	/*
    	 * 반환하는 숫자의 의미
    	 * 1: 회원가입 내역 없음
    	 * 2: 가입된 ID이지만, 이메일이 불일치
    	 * 3: 이메일로 임시 비밀번호 전송 성공
    	 * 4: 기타 서버 오류
    	 */
        switch(result) {
        case 1: return new ResponseEntity<>(1, HttpStatus.NOT_FOUND);
        case 2: return new ResponseEntity<>(2, HttpStatus.BAD_REQUEST);
        case 3: return new ResponseEntity<>(3, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(4, HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
}
