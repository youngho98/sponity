package com.ssafy.sponity.model.dao;

import java.util.Map;

import com.ssafy.sponity.model.dto.Club;

public interface ClubManagerDao {

	int insertClub(Club club);

	int selectMaxClubId();

	int insertMember(Map<String, Object> map);


}
