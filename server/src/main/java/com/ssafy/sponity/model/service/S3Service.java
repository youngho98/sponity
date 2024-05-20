package com.ssafy.sponity.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	
	String upload(String folderName, MultipartFile file, String userId) throws IOException;
	
	void delete(String folderName, String fileName, String userId);
	
}
