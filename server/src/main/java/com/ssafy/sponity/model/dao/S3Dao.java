package com.ssafy.sponity.model.dao;

import java.util.Map;

public interface S3Dao {

	int updateProfilePictureUrl(Map<String, String> map);

	void deleteProfilePictureUrl(String userId);

	int selectMaxBoardId();

	int updateBoardPictureUrl(Map<String, Object> map);

	int updateClubPictureUrl(Map<String, Object> map);

	void deleteClubPictureUrl(int clubId);
	
}
