package com.ssafy.sponity.model.service;

import com.ssafy.sponity.model.dto.User;

public interface UserService {
	
	int join(User user);
	
	String findId(String userName, String amail);
	
	int resetPassword(String userId, String email);
	
}
