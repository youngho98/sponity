package com.ssafy.sponity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sponity.model.dto.User;
import com.ssafy.sponity.model.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {
	
	// DI
	private UserService userService;
	public UserController (UserService userService) {
		this.userService = userService;
	}
	
    
    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
    	
        int result = userService.join(user);
        
        switch(result) {
        case 1: return new ResponseEntity<>("ID 중복", HttpStatus.CONFLICT);
        case 2: return new ResponseEntity<>("별명 중복", HttpStatus.CONFLICT);
        case 3: return new ResponseEntity<>("전화번호 중복", HttpStatus.CONFLICT);
        
        case 4: return new ResponseEntity<>("ID 규칙 위반", HttpStatus.BAD_REQUEST);
        case 5: return new ResponseEntity<>("비밀번호 규칙 위반", HttpStatus.BAD_REQUEST);

        case 6: return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
        }
        
        return new ResponseEntity<>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
}
