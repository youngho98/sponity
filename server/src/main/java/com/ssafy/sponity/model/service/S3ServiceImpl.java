package com.ssafy.sponity.model.service;

import java.io.IOException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements S3Service {

	@Value("${cloud.aws.s3.bucket}")
    private String bucket;  // 버킷은 이미지 객체를 가진 폴더를 담는 프로젝트와 같은 역할을 합니다.

    private final AmazonS3 amazonS3;
	
    
	// 파일 업로드
	@Override
	public String upload(MultipartFile file) throws IOException {
		
        // 파일을 다른 것과 식별할 key 생성
        // - 파일을 저장할 버킷 내의 폴더명과 파일명을 붙여 생성합니다.
        String key = "profile-picture/" + file.getOriginalFilename();
        
        // 메타데이터 생성
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(file.getInputStream().available());
        
		// putObject(버킷명, 키, 파일데이터, 메타데이터)로 S3에 객체 등록
        amazonS3.putObject(bucket, key, file.getInputStream(), objMeta);
        
		// 등록된 객체의 URL 반환 (decoder: URL 안의 한글or특수문자 깨짐 방지)
        return URLDecoder.decode(amazonS3.getUrl(bucket, key).toString(), "utf-8");
        
	}

	
	// 파일 삭제
	@Override
	public void delete(String fileName) {
		try {
			String key = "profile-picture/" + fileName;
			
        	// deleteObject(버킷명, 키값)으로 객체 삭제
            amazonS3.deleteObject(bucket, key);
        } catch (AmazonServiceException e) {
            log.error(e.toString());
        }
	}

}
