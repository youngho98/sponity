package com.ssafy.sponity.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Club;

public interface ClubDao {

	List<Club> selectClubList(Map<String, String> map);

	int countMember(int clubId);

	int countLike(int clubId);

	Club selectClub(int clubId);

}
