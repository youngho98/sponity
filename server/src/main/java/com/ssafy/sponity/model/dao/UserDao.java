package com.ssafy.sponity.model.dao;

import org.apache.ibatis.annotations.Param;

import com.ssafy.sponity.model.dto.User;

public interface UserDao {
	
	int join(User user);
	
	boolean existsById(String userId);
	
	boolean existsByNickname(String nickname);
	
	boolean existsByEmail(String phone);
	
	User findByUserId(String userId);
	
	String findId(@Param("userName") String userName, @Param("email") String amail);
	
}
