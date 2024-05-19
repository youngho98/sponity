package com.ssafy.sponity.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.dto.User;

public interface ClubManagerDao {

	int insertClub(Club club);

	int selectMaxClubId();

	int insertMember(Map<String, Object> map);

	boolean isLeader(Map<String, Object> map);

	int updateClub(Club club);

	int deleteClub(int clubId);

	List<User> selectMember(int clubId);

	int cancelLeader(int clubId);

	int grantLeader(int newLeaderId);

	int deleteMember(int memberId);


}
