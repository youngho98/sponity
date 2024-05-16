package com.ssafy.sponity.model.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
 * CustomUserDetailsService 클래스에서 로그인 인증이 완료된 사용자의 상세 정보를 나타내기 위해 사용되는 클래스입니다.
 * - 사용자의 ID, 비밀번호, 권한 등의 정보를 포함합니다.
 */
public class CustomUserDetails implements UserDetails {
	
	// DI
    private User user;
    public CustomUserDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
            	return "USER";
//              return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId(); // id를 반환하기
    }
    
    public String getNickname() {
        return user.getNickname();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}