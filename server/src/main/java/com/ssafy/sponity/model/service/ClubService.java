package com.ssafy.sponity.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Board;
import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.dto.Review;
import com.ssafy.sponity.model.dto.User;

public interface ClubService {

	List<Club> searchClub(Map<String, String> map);

	Club detailClub(int clubId);

	int userStatus(Map<String, Object> idMap);
	
	int isLike(Map<String, Object> idMap);
	
	List<User> searchMember(int clubId);
	
	int clubIn(Map<String, Object> idMap);

	int clubOut(Map<String, Object> idMap);

	int clubLike(Map<String, Object> idMap);

	int clubDislike(Map<String, Object> idMap);

	List<Board> boardList(int clubId);

	int createBoard(Board board);

	Board getBoard(int boardId);

	int modifyBoard(Board board);

	int removeBoard(int boardId);

	Review getReview(int reviewId);

	int createReview(Review review);

	int modifyReview(Map<String, Object> map);

	int removeReview(int reviewId);




}
