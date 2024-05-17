package com.ssafy.sponity.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	
	// 비밀번호 암호화에 사용하는 객체
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	// 내 정보 조회
	@Override
	public User searchMyInfo(String userId) {
		return mypageDao.selectByUserId(userId);
	}


	// 회원정보 수정
	@Override
	public int modifyProfile(User user) {
		// 닉네임 중복 검증
		boolean isNicknameExists = mypageDao.existsByNickname(user.getNickname());
		if (isNicknameExists) {
			return -1;
		}
		
		return mypageDao.updateUser(user);
	}


	// 비밀번호 변경
	@Override
	public int modifyPw(String curPw, String newPw, String userId) {
		
		// 비밀번호 체크
		// - bCryptPasswordEncoder의 matches 메서드를 이용해, 현재 비밀번호를 복호화하지 않고도 일치여부 확인이 가능합니다.
		String encryptedCurPw = mypageDao.getEncryptedCurPw(userId);
        if (!bCryptPasswordEncoder.matches(curPw, encryptedCurPw)) {
            return 1;
        }
		
		// 새로운 비밀번호가 비밀번호 규칙에 부합하는지 판단
		if (!newPw.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$")){
        	return 2;
        }
		
		// 비밀번호 변경
		String encryptedNewPw = bCryptPasswordEncoder.encode(newPw);
		int result = mypageDao.updatePw(encryptedNewPw, userId);
		
		if (result > 0) {
			return 3;
		}
		
		return -1;
		
	}
	
	
	

}
