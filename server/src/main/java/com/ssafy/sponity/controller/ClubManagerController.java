package com.ssafy.sponity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sponity.jwt.JWTUtil;
import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.service.ClubManagerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/club-manager")
public class ClubManagerController {
	
	// DI
	private final ClubManagerService clubManagerService;
	private final JWTUtil jwtUtil;
	public ClubManagerController(ClubManagerService clubManagerService, JWTUtil jwtUtil) {
		this.clubManagerService = clubManagerService;
		this.jwtUtil = jwtUtil;
	}
	
	
	// 모임 생성
    @PostMapping("")
    public ResponseEntity<String> makeClub(@RequestBody Club club, HttpServletRequest request) {
    	// 생성한 유저의 아이디 - 모임장으로 등록됨
    	String token = request.getHeader("Authorization").split(" ")[1];
    	String userId = jwtUtil.getUserId(token);
    	
    	int result = clubManagerService.makeClub(club, userId);
    	
    	if (result > 0) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
    
	// 모임 수정
	
	
	// 모임 삭제
	
	
	
}
