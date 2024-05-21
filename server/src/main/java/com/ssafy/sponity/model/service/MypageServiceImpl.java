package com.ssafy.sponity.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.sponity.model.dao.MypageDao;
import com.ssafy.sponity.model.dto.Club;
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
	public int modifyProfile(User user, String curNickname, String curEmail) {
		// 닉네임 중복 검증
		// - 단 "토큰에서 추출한 현재 닉네임"과 "입력받은 새로운 닉네임"이 같은 경우, 닉네임을 바꾸지 않겠다는 의미이므로, 중복 검증을 하지 않습니다.
		String newNickname = user.getNickname();
		if (!curNickname.equals(newNickname)) {
			boolean isNicknameExists = mypageDao.isNicknameExists(newNickname);
			if (isNicknameExists) {
				return 1;
			}
		}
		
		// 이메일 중복 검증
		// - 단 "토큰에서 추출한 현재 이메일"과 "입력받은 새로운 이메일"이 같은 경우, 이메일을 바꾸지 않겠다는 의미이므로, 중복 검증을 하지 않습니다.
		String newEmail = user.getEmail();
		if (!curEmail.equals(newEmail)) {
			boolean isEmailExists = mypageDao.isEmailExists(newEmail);
			if (isEmailExists) {
				return 2;
			}
		}
		
		int result = mypageDao.updateUser(user);
		
		if (result > 0) {
			return 3;
		}
		
		return -1;
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


	// 회원 탈퇴
	@Override
	public int withdraw(String password, String userId) {
		// 비밀번호 체크
		String encryptedPassword = mypageDao.getEncryptedCurPw(userId);
        if (!bCryptPasswordEncoder.matches(password, encryptedPassword)) {
            return 1;
        }
		
        int result = mypageDao.withdraw(userId);
        
        if (result > 0) {
        	return 2;
        }
        
		return -1;
	}


	// ----- 내가 가입한 클럽 목록 확인 -----------------------------------------------------------------------------------------------

	
	@Override
	public List<Club> getMyClubList(String userId) {
		// member 테이블에서 userId로 가입된 clubId 검색
		List<Integer> clubIdList = mypageDao.selectMyClubId(userId);
		
		// club 테이블에서 clubId에 해당하는 클럽 검색
		List<Club> myClubList = new ArrayList<>();
		
		for (int i = 0; i < clubIdList.size(); i++) {
			int clubId = clubIdList.get(i);
			Club club = mypageDao.selectMyClub(clubId);
			
			myClubList.add(club);
		}
		
		return myClubList;
	}

}
