package com.ssafy.sponity.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.dto.User;

public interface ClubManagerService {

	int makeClub(Club club, String loginId);

	int modifyClub(Club club, String loginId);

	int removeClub(int clubId, String loginId);

	List<User> searchMember(int clubId);

	int leaderChange(int clubId, int newLeaderId);

	int expelMember(int memberId);
}
