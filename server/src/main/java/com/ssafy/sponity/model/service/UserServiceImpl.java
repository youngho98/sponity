package com.ssafy.sponity.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.sponity.model.dao.UserDao;
import com.ssafy.sponity.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	// DI
	private UserDao userDao;
	public UserServiceImpl (UserDao userDao) {
		this.userDao = userDao;
	}
	
	// 비밀번호 암호화에 사용하는 객체
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	// 회원가입
	@Override
    public int join(User user) {
		
		/*
		 *  회원 중복 검증
		 *  - 각각 ID, 별명, 전화번호 중복여부를 확인합니다.
		 */
		boolean isIdExists = userDao.existsById(user.getUserId());
		if (isIdExists) {
			return 1;
		}
		boolean isNicknameExists = userDao.existsByNickname(user.getNickname());
		if (isNicknameExists) {
			return 2;
		}
		boolean isEmailExists = userDao.existsByEmail(user.getEmail());
		if (isEmailExists) {
			return 3;
		}
		
		
		/*
		 *  가입불가 문자 정규식 처리
		 *  - 아이디가 알파벳 대소문자와 숫자로만 이루어져 있으면서 4개 이상 14개 이하인지 확인합니다.
		 *  - 비밀번호가 알파벳 대소문자, 숫자, 특수문자를 각각 적어도 하나 이상 포함하면서 8개 이상 20개 이하인지 확인합니다.
		 */
        if (!user.getUserId().matches("^[a-zA-Z0-9]{4,14}$")){
            return 4;
        }
        if (!user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$")){
        	return 5;
        }
        
        
        // 이 구역에 들어온 경우, 클라이언트의 요청은 적절합니다.
        
		// 비밀번호 암호화
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        int result = userDao.join(user);
        if (result > 0) {
        	return 6;
        }
        
        return -1;
    }

	
	// 아이디 찾기
	@Override
	public String findId(String userName, String amail) {
		return userDao.findId(userName, amail);
	}
	
	
	// 비밀번호 찾기
}
