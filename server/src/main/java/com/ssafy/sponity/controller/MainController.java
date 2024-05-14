package com.ssafy.sponity.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainP() {
    	
    	// 세션에 현재 로그인한 사용자의 아이디
    	String id = SecurityContextHolder.getContext().getAuthentication().getName();
    	
//    	// 세션에 현재 로그인한 사용자의 role
//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    	Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//    	Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//    	GrantedAuthority auth = iter.next();
//    	String role = auth.getAuthority();
    	
        return "main controller : " + id;
    }
}