package com.ssafy.sponity.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	
	String profilePictureUpload(MultipartFile file, String userId) throws IOException;
	
	void profilePictureDelete(String fileName, String userId);
	
}
