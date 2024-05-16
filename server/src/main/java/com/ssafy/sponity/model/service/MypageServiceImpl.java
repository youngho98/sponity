package com.ssafy.sponity.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.sponity.model.dao.MypageDao;
import com.ssafy.sponity.model.dto.User;

@Service
public class MypageServiceImpl implements MypageService {
	
	// DI
	private MypageDao mypageDao;
	public MypageServiceImpl (MypageDao mypageDao) {
		this.mypageDao = mypageDao;
	}
	
	
	// 내 정보 조회
	@Override
	public User searchMyInfo(String nickname) {
		return mypageDao.selectByNickname(nickname);
	}

}
