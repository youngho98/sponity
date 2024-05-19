package com.ssafy.sponity.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.sponity.model.dao.ClubManagerDao;
import com.ssafy.sponity.model.dto.Club;

@Service
public class ClubManagerServiceImpl implements ClubManagerService {

	// DI
	private final ClubManagerDao clubManagerDao;
	public ClubManagerServiceImpl(ClubManagerDao clubManagerDao) {
		this.clubManagerDao = clubManagerDao;
	}
	
	
	// 모임 생성
	@Override
	public int makeClub(Club club, String loginId) {
		// club 레코드 생성
		int clubResult = clubManagerDao.insertClub(club);
		
		// 방금 만들어진 club 레코드의 clubId를 가져오기
		int clubId = clubManagerDao.selectMaxClubId();
		
		// member 레코드 생성 - 생성한 사용자가 모임장이 됨
		Map<String, Object> map = new HashMap<>();
		map.put("userId", loginId);
		map.put("clubId", clubId);
		map.put("leader", "Y");
		int memberResult = clubManagerDao.insertMember(map);
		
		if (clubResult > 0 && memberResult > 0) {
			return 1;
		}
		
		return -1;
	}


	// 모임 수정
	@Override
	public int modifyClub(Club club, String loginId) {
		// 모임장이 맞는지 확인
		// - 모임장에 한해 모임 정보를 수정할 수 있게 해야합니다.
		Map<String, Object> map = new HashMap<>();
		map.put("userId", loginId);
		map.put("clubId", club.getClubId());
		boolean isLeader = clubManagerDao.isLeader(map);
		if(!isLeader) {
			return 1;
		}
		
		int result = clubManagerDao.updateClub(club);
		
		if (result > 0) {
			return 2;
		}
		
		return -1;
	}


	// 모임 삭제
	@Override
	public int removeClub(int clubId, String loginId) {
		// 모임장이 맞는지 확인
		// - 모임장에 한해 모임을 삭제할 수 있게 해야합니다.
		Map<String, Object> map = new HashMap<>();
		map.put("userId", loginId);
		map.put("clubId", clubId);
		boolean isLeader = clubManagerDao.isLeader(map);
		if(!isLeader) {
			return 1;
		}
		
		int result = clubManagerDao.deleteClub(clubId);
		
		if (result > 0) {
			return 2;
		}
		
		return -1;
	}
	
}
