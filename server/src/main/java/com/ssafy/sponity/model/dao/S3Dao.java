package com.ssafy.sponity.model.dao;

import java.util.Map;

public interface S3Dao {

	int updateprofilePictureUrl(Map<String, String> map);

	void deleteprofilePictureUrl(String userId);

	int selectMaxBoardId();

	int updateBoardPictureUrl(Map<String, Object> map);
	
}
