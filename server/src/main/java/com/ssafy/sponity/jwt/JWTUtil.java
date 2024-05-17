package com.ssafy.sponity.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

/*
 * JWT(Json Web Token)의 발급과 검증을 담당하는 클래스입니다.
 */
@Component
public class JWTUtil {
    
    // application.properties에 저장된 암호화 키를 기반으로 토큰을 암호화
	private SecretKey secretKey;
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    
    // JWT 확인
    // - 비밀키로 토큰 검증, 클레임 파싱, 클레임에서 속성 값 추출 등의 순서를 거칩니다.
    public String getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", String.class);
    }
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }
    public String getNickname(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("nickname", String.class);
    }
    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    
    // JWT 발급
    // - 토큰의 payload 부분에 claim을 저장하고, 발급 및 만료 시간을 설정합니다.
    public String createJwt(String userId, String nickname, String email, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("nickname", nickname)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}