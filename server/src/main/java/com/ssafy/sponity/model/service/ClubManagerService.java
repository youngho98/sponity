package com.ssafy.sponity.model.service;

import com.ssafy.sponity.model.dto.Club;

public interface ClubManagerService {

	int makeClub(Club club, String loginId);

	int modifyClub(Club club, String loginId);

	int removeClub(int clubId, String loginId);

}
