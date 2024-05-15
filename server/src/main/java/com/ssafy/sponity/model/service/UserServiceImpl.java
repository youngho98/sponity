package com.ssafy.sponity.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	
	// 이메일 설정
	@Autowired
	private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "sponity@naver.com";
	
	
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
	public String findId(String userName, String email) {
		return userDao.findId(userName, email);
	}


	// 비밀번호 재설정
	@Override
	public int resetPassword(String userId, String email) {
		
		// 가입된 회원인지 조회
		User user = userDao.selectByUserId(userId);
		if(user == null) {
			return 1;
		}
		
		// 이메일 일치여부 확인
		if(!email.equals(user.getEmail())) {
			return 2;
		}
		
		// 임시 비밀번호 생성
		String tempPassword = "";

		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        for (int i = 0; i < 8; i++) {
            int idx = (int) (charSet.length * Math.random());
            tempPassword += charSet[idx];
        }
		
        // 임시 비밀번호 암호화 및 DB 갱신
        // - 주의 : "암호화 된" 비밀번호로 갱신해야 합니다.
        String encryptedPassword = bCryptPasswordEncoder.encode(tempPassword);
		userDao.updatePassword(userId, encryptedPassword);
		
		// 임시 비밀번호를 이메일로 전송
		// - 주의 : "암호화되지 않은" 비밀번호를 전송해야 합니다.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(email);
        message.setSubject("[SPONITY] " + userId + "님의 임시 비밀번호 안내 이메일입니다.");
        message.setText("안녕하세요. SPONITY입니다.\n" + user.getUserName() + "님의 임시 비밀번호는 " + tempPassword + "입니다.\n" + "로그인 후 즉시 비밀번호를 변경해주세요.");

        mailSender.send(message);
        
		return 3;
	}
	
}
