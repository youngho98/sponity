package com.ssafy.sponity.model.dao;

import java.util.Map;

import com.ssafy.sponity.model.dto.Club;

public interface ClubManagerDao {

	int insertClub(Club club);

	int selectMaxClubId();

	int insertMember(Map<String, Object> map);

	boolean isLeader(Map<String, Object> map);

	int updateClub(Club club);

	int deleteClub(int clubId);

	int cancelLeader(int clubId);

	int grantLeader(Map<String, Object> map);

	int deleteMember(Map<String, Object> map);

	String selectClubPictureUrl(int clubId);


}
