package com.ssafy.sponity.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.sponity.model.dto.CustomUserDetails;
import com.ssafy.sponity.model.dto.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 이 필터는 요청이 들어올 때마다 JWT를 확인하고 검증합니다. 
 * - 토큰이 유효한 경우, 해당 사용자의 인증 정보를 SecurityContext에 저장합니다.
 * - OncePerRequestFilter를 상속하여 구현되었기 때문에 각 요청에 대해 한 번만 실행됩니다.
 */
public class JWTFilter extends OncePerRequestFilter {

	// DI
    private final JWTUtil jwtUtil;
    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
				
		// request의 Authorization 헤더 검증
        String authorization= request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("토큰이 없습니다.");
            filterChain.doFilter(request, response); // permitAll() 처리된 경로가 아니라면, 다음 절차인 LoginFilter에서 차단됩니다.
            return;
        }
        
		// Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];
			
		// 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("만료된 토큰입니다.");
            filterChain.doFilter(request, response); // permitAll() 처리된 경로가 아니라면, 다음 절차인 LoginFilter에서 차단됩니다.
            return;
        }
        
        
        System.out.println("토큰이 인증되었습니다.");

        
        // 토큰에서 userId과 role 획득
        String userId = jwtUtil.getUserId(token);
//        String role = jwtUtil.getRole(token);
				
		// user 객체를 생성하여 값 set
        User user = new User();
        user.setUserId(userId);
//        user.setRole(role);
				
        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

		// 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // SecurityContext에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}