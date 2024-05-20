package com.ssafy.sponity.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Club;

public interface ClubService {

	List<Club> searchClub(Map<String, String> map);

	Club detailClub(int clubId);

	int userStatus(Map<String, Object> map);
	
	int isLike(Map<String, Object> map);
	
	int clubIn(Map<String, Object> map);

	int clubOut(Map<String, Object> map);



}
