package com.ssafy.sponity.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Board;
import com.ssafy.sponity.model.dto.Club;

public interface ClubService {

	List<Club> searchClub(Map<String, String> map);

	Club detailClub(int clubId);

	int userStatus(Map<String, Object> idMap);
	
	int isLike(Map<String, Object> idMap);
	
	int clubIn(Map<String, Object> idMap);

	int clubOut(Map<String, Object> idMap);

	int clubLike(Map<String, Object> idMap);

	int clubDislike(Map<String, Object> idMap);

	List<Board> boardList(int clubId);



}
