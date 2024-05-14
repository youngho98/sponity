package com.ssafy.sponity.jwt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.AuthenticationManager;
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

	// JSON 타입의 요청 데이터를 파싱해 userId를 추출하기 위해 사용하는 객체
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	
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
            InputStream inputStream = request.getInputStream();
            if (inputStream == null) {
                System.out.println("InputStream is null");
                throw new AuthenticationException("Request InputStream is null") {};
            }

            // JSON 형식을 LoginRequest 타입으로 파싱 후, userId와 password 추출
            LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
            if (loginRequest == null) {
                System.out.println("loginRequest is null after parsing");
                throw new AuthenticationException("Failed to parse authentication request body") {};
            }

            String userId = loginRequest.getUserId();
            String password = loginRequest.getPassword();

            // 로그인 인증정보를 토큰에 담기
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password, null);

            // 로그인 인증을 수행하는 AuthenticationManager로 토큰을 전달
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            System.out.println("IOException during authentication attempt: " + e.getMessage());
            throw new AuthenticationException("Failed to parse authentication request body", e) {};
        }
    }

    
	// 로그인 성공 - JWT 발급
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userId, role, 60*60*1000L); // 유효시간 : 1시간

        response.addHeader("Authorization", "Bearer " + token);
    }

    
	// 로그인 실패 - Unauthorized(401) 상태코드 반환
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    	response.setStatus(401);
    }
    
    
    // JSON 요청을 담을 클래스
    @Data
    static class LoginRequest {
        private String userId;
        private String password;
    }
}

