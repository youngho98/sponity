package com.ssafy.sponity.model.dao;

import java.util.Map;

public interface S3Dao {

	int updateImgUrl(Map<String, String> map);

	void deleteImgUrl(String userId);
	
}
