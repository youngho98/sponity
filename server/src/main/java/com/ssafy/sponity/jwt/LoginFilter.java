package com.ssafy.sponity.jwt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.sponity.model.dto.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

/*
 * 로그인 인증을 진행하는 AuthenticationManager를 호출하는 필터입니다.
 * - AuthenticationManager는 로그인 컨트롤러와 같이 입력값과 DB에 저장된 인증정보를 대조하는 역할을 수행하고, 이 클래스에 인증결과를 반환합니다.
 * - 그 인증결과를 받은 이 클래스는 로그인 성공시 JWT를 클라이언트에 반환합니다.
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	
	// DI
	// - JWT도 함께 주입
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    
    
    // 로그인 인증
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
        	// 사용자 정보 추출을 위해 JSON 형식의 요청을 DTO 형식으로 매핑
            ObjectMapper objectMapper = new ObjectMapper();
            LoginDTO loginDTO = objectMapper.readValue(request.getInputStream(), LoginDTO.class);

            String userId = loginDTO.getUserId();
            String password = loginDTO.getPassword();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new BadCredentialsException("Failed to parse authentication request body", e);
        }
    }

    
	// 로그인 성공 - JWT 발급
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {	
    	CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = customUserDetails.getUsername();
        String nickname = customUserDetails.getNickname();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userId, nickname, role, 10*60*60*1000L); // 유효시간 : 10시간

        response.addHeader("Authorization", "Bearer " + token);
    }

    
	// 로그인 실패 - Unauthorized(401) 상태코드 반환
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
    	response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
    
    
    // JSON 요청을 담을 클래스
    @Data
    static class LoginDTO {
        private String userId;
        private String password;
    }
    
}

