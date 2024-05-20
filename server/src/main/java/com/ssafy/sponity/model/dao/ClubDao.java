package com.ssafy.sponity.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Club;

public interface ClubDao {

	List<Club> selectClubList(Map<String, String> map);

	int countMember(int clubId);

	int countLike(int clubId);

	String userStatus(Map<String, Object> map);

	boolean isLike(Map<String, Object> map);

	Club selectClub(int clubId);

	boolean isJoined(Map<String, Object> map);

	int insertMember(Map<String, Object> map);

	int deleteMember(Map<String, Object> map);




}
