package com.ssafy.sponity.model.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.sponity.model.dao.ClubDao;
import com.ssafy.sponity.model.dto.Club;

@Service
public class ClubServiceImpl implements ClubService {
	
	// DI
	private final ClubDao clubDao;
	public ClubServiceImpl(ClubDao clubDao) {
		this.clubDao = clubDao;
	}
	
	
	// 모임 검색
	@Override
	public List<Club> searchClub(Map<String, String> map) {
		List<Club> result = clubDao.selectClub(map);
		
		if (result == null) {
			return null;
		}
		
		// 각 모임의 회원수 및 좋아요수 계산
		for (int i = 0; i < result.size(); i++) {
			Club club = result.get(i);
			
			int clubId = club.getClubId();
			int memberNum = clubDao.countMember(clubId);
			int likeNum = clubDao.countLike(clubId);
			
			club.setMemberNum(memberNum);
			club.setLikeNum(likeNum);
		}
		
        // 멤버 수를 기준으로 내림차순 정렬
		// - 검색결과를 단순히 반환하기보다는, 기본값을 멤버가 많은 모임부터 보이도록 하기 위함입니다.
        Collections.sort(result, new Comparator<Club>() {
            @Override
            public int compare(Club club1, Club club2) {
                return club2.getMemberNum() - club1.getMemberNum();
            }
        });
		
		return result;
	}
	
	
	
	
	
	
}