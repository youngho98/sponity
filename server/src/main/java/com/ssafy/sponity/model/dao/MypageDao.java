package com.ssafy.sponity.model.dao;

import com.ssafy.sponity.model.dto.User;

public interface MypageDao {

	User selectByNickname(String nickname);

}
