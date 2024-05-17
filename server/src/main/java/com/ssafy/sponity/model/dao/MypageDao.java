package com.ssafy.sponity.model.dao;

import org.apache.ibatis.annotations.Param;

import com.ssafy.sponity.model.dto.User;

public interface MypageDao {

	User selectByUserId(String userId);
	
	boolean existsByNickname(String nickname);

	int updateUser(User user);

	String getEncryptedCurPw(String userId);

	int updatePw(@Param("encryptedNewPw") String encryptedNewPw, @Param("userId") String userId);
	
}
