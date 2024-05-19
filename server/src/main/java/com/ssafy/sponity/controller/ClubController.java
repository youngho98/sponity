package com.ssafy.sponity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.sponity.model.dto.Club;
import com.ssafy.sponity.model.service.ClubService;

import lombok.Data;

@RestController
@RequestMapping("/club")
public class ClubController {
	
	// DI
	private final ClubService clubService;
	public ClubController (ClubService clubService) {
		this.clubService = clubService;
	}
	
	// 모임 검색
	@PostMapping("/search")
	public ResponseEntity<List<Club>> searchClub(@RequestBody SearchDTO searchDTO) {
		Map<String, String> map = new HashMap<>();
		map.put("category", searchDTO.getCategory());
		map.put("wideArea", searchDTO.getWideArea());
		map.put("detailArea", searchDTO.getDetailArea());
		map.put("keyword", searchDTO.getKeyword());
		
		List<Club> result = clubService.searchClub(map);
		
		if (result != null) {
			// RestController에서 리스트 반환시, 자동으로 JSON으로 변환됨
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@Data
	public static class SearchDTO {
		private String wideArea;
		private String detailArea;
		private String category;
		private String keyword;
	}
}
