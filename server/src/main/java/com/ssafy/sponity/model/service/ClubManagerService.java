package com.ssafy.sponity.model.service;

import java.util.Map;

import com.ssafy.sponity.model.dto.Club;

public interface ClubManagerService {

	int makeClub(Club club, String loginId);

	int modifyClub(Club club, String loginId);

	int removeClub(int clubId, String loginId);

	int leaderChange(Map<String, Object> map);

	int expelMember(Map<String, Object> map);
}
