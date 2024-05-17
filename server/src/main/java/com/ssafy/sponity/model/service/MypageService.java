package com.ssafy.sponity.model.service;

import com.ssafy.sponity.model.dto.User;

public interface MypageService {

	User searchMyInfo(String userId);

	int modifyProfile(User user);

	int modifyPw(String curPw, String newPw, String userId);

}
