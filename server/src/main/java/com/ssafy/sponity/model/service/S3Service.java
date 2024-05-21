package com.ssafy.sponity.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	
	String uploadProfilePicture(MultipartFile file, String userId) throws IOException;
	
	void deleteProfilePicture(String fileName, String userId);
	
	String uploadBoardPicture(String colName, MultipartFile file) throws IOException;

	String modifyBoardPicture(MultipartFile file) throws IOException;

	String uploadClubPicture(MultipartFile file, int clubId) throws IOException;

	void deleteClubPicture(String fileName, int clubId);
	
}
