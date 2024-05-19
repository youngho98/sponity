package com.ssafy.sponity.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.sponity.model.dto.Club;

public interface ClubService {

	List<Club> searchClub(Map<String, String> map);

}
