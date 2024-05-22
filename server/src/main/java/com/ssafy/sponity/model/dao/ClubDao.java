package com.ssafy.sponity.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Board;
import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.dto.Review;
import com.ssafy.sponity.model.dto.User;

public interface ClubDao {

	List<Club> selectClubList(Map<String, String> map);

	int countMember(int clubId);

	int countLike(int clubId);

	String userStatus(Map<String, Object> idMap);

	boolean isLike(Map<String, Object> idMap);
	
	List<User> selectMember(int clubId);

	Club selectClub(int clubId);

	boolean isJoined(Map<String, Object> idMap);

	int insertMember(Map<String, Object> idMap);

	int deleteMember(Map<String, Object> idMap);

	int clubLike(Map<String, Object> idMap);

	int clubDislike(Map<String, Object> idMap);

	List<Board> selectBoardList(int clubId);

	int insertBoard(Board board);

	Board selectBoard(int boardId);

	void increaseViewCnt(int boardId);

	String selectNickname(String userId);

	List<Review> selectReviewList(int boardId);

	int updateBoard(Board board);

	int deleteBoard(int boardId);

	Review selectReview(int reviewId);

	int insertReview(Review review);

	int updateReview(Review review);

	int deleteReview(int reviewId);

}
