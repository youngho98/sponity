package com.ssafy.sponity.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	
	String upload(MultipartFile file) throws IOException;
	
	void delete(String fileName);
	
}
