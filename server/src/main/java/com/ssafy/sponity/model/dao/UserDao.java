package com.ssafy.sponity.model.dao;

import com.ssafy.sponity.model.dto.User;

public interface UserDao {
	
	public int join(User user);
	
	public boolean existsById(String userId);
	
	public boolean existsByNickname(String nickname);
	
	public boolean existsByPhone(String phone);
	
	User findByUserId(String userId);
}
