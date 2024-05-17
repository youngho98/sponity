package com.ssafy.sponity.model.dao;

import org.apache.ibatis.annotations.Param;

import com.ssafy.sponity.model.dto.User;

public interface MypageDao {

	User selectByUserId(String userId);
	
	boolean isNicknameExists(String nickname);

	boolean isEmailExists(String newEmail);

	int updateUser(User user);

	String getEncryptedCurPw(String userId);

	int updatePw(@Param("encryptedNewPw") String encryptedNewPw, @Param("userId") String userId);

	int withdraw(String userId);

}
