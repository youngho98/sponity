package com.ssafy.sponity.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ssafy.sponity.jwt.JWTFilter;
import com.ssafy.sponity.jwt.JWTUtil;
import com.ssafy.sponity.jwt.LoginFilter;

import jakarta.servlet.http.HttpServletRequest;

/*
 * Spring Security와 관련된 설정을 하는 클래스입니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// 로그인 인증을 위한 DI
	// - JWT도 함께 주입
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
	    this.authenticationConfiguration = authenticationConfiguration;
	    this.jwtUtil = jwtUtil;
	}
	// AuthenticationManager Bean 등록
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
    
	
	// BCrypt 암호화 메서드 - 비밀번호를 암호화
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	// Spring Security Filter 역할을 하는 메서드
	// - 클라이언트의 요청이 DispatcherServlet에 도달하기 전에 가로채 로그인 인증, 예외 처리, 요청 인가 등의 역할을 수행합니다. 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
		// CSRF disable
        http
                .csrf((auth) -> auth.disable());

		// From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

		// http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

		/*
		 * # 요청 인가
		 * 1. "/join", "/login", "/find-id", "/reset-pw"
		 *   회원가입, 로그인, 아이디 찾기, 비밀번호 재설정 등은 로그인 하지 않은 상태에서도 접근할 수 있어야 합니다.
		 *   따라서 JWT 토큰이 없어도 접근할 수 있도록 permitAll()로 설정하였습니다.
		 * 2. "/admin"
		 *   관리자 페이지이므로 접근하려면 JWT를 가지고 있어야 하며, 토큰 내의 권한 정보에 ADMIN 역할이 포함되어 있어야 합니다.
		 * 3. 그 외
		 *   위 경로 외의 모든 요청은 로그인 인증되어 JWT를 소지한 사용자만 접근할 수 있습니다.
		 */
        // 
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/join", "/login", "/find-id", "/reset-pw").permitAll()
						.requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

		// JWT Filter 등록
        // - LoginFilter 이전에 등록하여 JWT 기반 인증을 처리합니다.
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        
        // 로그인 인증 필터 등록
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        
		// 세션 설정 
        // - JWT 기반 인증에서는 세션을 stateless로 유지합니다.
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        // CORS 관련 시큐리티측 설정
        http
        .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(3600L);

				configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                return configuration;
            }
        })));
        
        return http.build();
    }
    
}