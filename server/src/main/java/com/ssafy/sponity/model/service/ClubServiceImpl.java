package com.ssafy.sponity.model.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.sponity.model.dao.ClubDao;
import com.ssafy.sponity.model.dto.Board;
import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.dto.User;

@Service
public class ClubServiceImpl implements ClubService {
	
	// DI
	private final ClubDao clubDao;
	public ClubServiceImpl(ClubDao clubDao) {
		this.clubDao = clubDao;
	}
	
	
	// 클럽 검색
	@Override
	public List<Club> searchClub(Map<String, String> map) {
		List<Club> result = clubDao.selectClubList(map);
		
		if (result == null) {
			return null;
		}
		
		// 각 클럽의 회원수 및 좋아요수 계산
		for (int i = 0; i < result.size(); i++) {
			Club club = result.get(i);
			
			int clubId = club.getClubId();
			int memberNum = clubDao.countMember(clubId);
			int likeNum = clubDao.countLike(clubId);
			
			club.setMemberNum(memberNum);
			club.setLikeNum(likeNum);
		}
		
        // 멤버 수를 기준으로 내림차순 정렬
		// - 검색결과를 단순히 반환하기보다는, 기본값을 멤버가 많은 클럽부터 보이도록 하기 위함입니다.
        Collections.sort(result, new Comparator<Club>() {
            @Override
            public int compare(Club club1, Club club2) {
                return club2.getMemberNum() - club1.getMemberNum();
            }
        });
		
		return result;
	}
	

	// 클럽 상세조회
	@Override
	public Club detailClub(int clubId) {
		// 해당 클럽의 회원수 및 좋아요수 세팅
		int memberNum = clubDao.countMember(clubId);
		int likeNum = clubDao.countLike(clubId);
		
		Club club = clubDao.selectClub(clubId);
		
		club.setMemberNum(memberNum);
		club.setLikeNum(likeNum);
		
		return club;
	}
	
	// 클럽 상세조회 - 해당 클럽에 대한 사용자의 지위
	@Override
	public int userStatus(Map<String, Object> idMap) {
		// 가입여부 확인
		boolean isJoined = clubDao.isJoined(idMap);
		if(!isJoined) {
			return 1;  // 클럽 미가입자
		}
		
		String status = clubDao.userStatus(idMap);
		
		if (status.equals("N")) {
			return 2;  // 일반 회원
		} else {
			return 3;  // 클럽장
		}
	}
	
	// 클럽 상세조회 - 해당 클럽에 대한 사용자의 좋아요 여부
	@Override
	public int isLike(Map<String, Object> idMap) {
		boolean isLike = clubDao.isLike(idMap);
		
		if(isLike) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
	// 모임 회원 조회
	@Override
	public List<User> searchMember(int clubId) {
		return clubDao.selectMember(clubId);
	}
	

	// 클럽 가입
	@Override
	public int clubIn(Map<String, Object> idMap) {
		// 기존 가입여부 확인
		boolean isJoined = clubDao.isJoined(idMap);
		if(isJoined) {
			return 1;
		}
		
		int result = clubDao.insertMember(idMap);
		
		if(result > 0) {
			return 2;
		}
		
		return -1;
	}


	// 클럽 탈퇴
	@Override
	public int clubOut(Map<String, Object> map) {
		// 기존 가입여부 확인
		boolean isJoined = clubDao.isJoined(map);
		if(!isJoined) {
			return 1;
		}
		
		int result = clubDao.deleteMember(map);
		
		if(result > 0) {
			return 2;
		}
		
		return -1;
	}

	
	// ----- 클럽 게시판 기능 ----------------------------------------------------------------------------------------------------
	
	
	// 클럽 좋아요
	@Override
	public int clubLike(Map<String, Object> idMap) {
		return clubDao.clubLike(idMap);
	}


	// 클럽 좋아요 취소
	@Override
	public int clubDislike(Map<String, Object> idMap) {
		return clubDao.clubDislike(idMap);
	}


	// ------ 클럽 게시판 내 댓글 기능 ---------------------------------------------------------------------------------------------------

	
	// 클럽 내 게시판 조회
	@Override
	public List<Board> boardList(int clubId) {
		return clubDao.selectBoardList(clubId);
	}





	
	
	
	
	
	
	
}
