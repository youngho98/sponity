package com.ssafy.sponity.model.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.sponity.model.dao.S3Dao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3ServiceImpl implements S3Service {

	// DI
	private final S3Dao s3Dao;
	private final AmazonS3 amazonS3;
	public S3ServiceImpl(S3Dao s3Dao, AmazonS3 amazonS3) {
		this.s3Dao = s3Dao;
		this.amazonS3 = amazonS3;
	}
	
	@Value("${cloud.aws.s3.bucket}")
    private String bucket;  // 버킷은 이미지 객체를 가진 폴더를 담는 프로젝트와 같은 역할을 합니다.

	
    
	// 파일 업로드
	@Override
	public String upload(MultipartFile file, String userId) throws IOException {
		
        // 파일을 다른 것과 식별할 key 생성
        // - 파일을 저장할 버킷 내의 폴더명과 파일명을 붙여 생성합니다.
		// - 파일명이 중복될 경우를 대비해 파일명에 현재 시간을 덧붙여줍니다.
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename() ;
        String key = "profile-picture/" + fileName;
        
        // 메타데이터 생성
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(file.getInputStream().available());
        
		// putObject(버킷명, 키, 파일데이터, 메타데이터)로 S3에 객체 등록
        amazonS3.putObject(bucket, key, file.getInputStream(), objMeta);
        
        // 등록된 객체의 URL (decoder: URL 안의 한글or특수문자 깨짐 방지)
        String url = URLDecoder.decode(amazonS3.getUrl(bucket, key).toString(), "utf-8");
        
        // 파일이 저장된 S3 서버의 URL을 DB에 저장
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        map.put("userId", userId);
        int result = s3Dao.updateImgUrl(map);
        
        if (result > 0) {
        	return url;        	
        } else {
        	return null;
        }
        
	}

	
	// 파일 삭제
	@Override
	public void delete(String fileName, String userId) {
		try {
			String key = "profile-picture/" + fileName;
			
        	// deleteObject(버킷명, 키값)으로 객체 삭제
            amazonS3.deleteObject(bucket, key);
            
            // DB에 저장된 이미지 파일 URL 삭제
            s3Dao.deleteImgUrl(userId);
        } catch (AmazonServiceException e) {
            log.error(e.toString());
        }
	}

}
