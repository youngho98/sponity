package com.ssafy.sponity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@RequestMapping("/mail")
public class MailController {
	
	// 이메일 설정
	@Autowired
	private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "sponity@naver.com";
    
    // 이메일 전송
    public void claimMail(@RequestBody MailDTO mailDTO) {
    	SimpleMailMessage claimMail = new SimpleMailMessage();
    	
    	claimMail.setFrom(FROM_ADDRESS);
    	claimMail.setTo(mailDTO.getEmail());
    	claimMail.setSubject(mailDTO.getName() + " 회원님으로부터 접수된 불편접수 이메일입니다.");
    	claimMail.setText(mailDTO.getMessage());
    	
    	mailSender.send(claimMail);
    }
    
    
    @Data
    static class MailDTO {
    	String name;
    	String email;
    	String message;
    }
}
