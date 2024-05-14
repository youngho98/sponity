package com.ssafy.sponity.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.sponity.model.dao.UserDao;
import com.ssafy.sponity.model.dto.CustomUserDetails;
import com.ssafy.sponity.model.dto.User;

/*
 * DB를 기반으로 로그인 인증을 수행하는 클래스입니다.
 * - 사용자가 로그인 POST 요청시, Spring Security가 DB에 저장된 회원정보를 조회 후 비밀번호를 검증하고,
 *   인증이 완료되면 사용자 인증정보는 세션 스코프에 SecurityContext 객체 안의 Authentication 객체로 저장됩니다.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	// DI
	private UserDao userDao;
	public CustomUserDetailsService (UserDao userdao) {
		this.userDao = userdao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userDao.findByUserId(userId);
		
        if (user == null) {
        	throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
        return new CustomUserDetails(user);
	}

}
