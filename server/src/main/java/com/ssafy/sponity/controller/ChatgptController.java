package com.ssafy.sponity.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@RequestMapping("/ai-rec")
public class ChatgptController {
    
    @Value("${chatgpt.apikey}")
    private String apiKey;
    private static final String GPT_URL = "https://api.openai.com/v1/chat/completions";
    
    private String searchCondition = 
    		  "1. 장소는 3가지를 제시하시오. "
    		+ "2. 다음과 같이 2차원 List 형식으로 답하시오. "
    		+ "  { {\"장소1의 이름\", \"장소1의 위도\", \"장소1의 경도\", \"추천 이유\"},"
    		+ "    {\"장소2의 이름\", \"장소2의 위도\", \"장소2의 경도\", \"추천 이유\"},"
    		+ "    {\"장소3의 이름\", \"장소3의 위도\", \"장소3의 경도\", \"추천 이유\"} }"
    		+ "3. 추천 이유는 각각 한 줄로만 설명하시오.";
    		
    
    
    // 운동 장소 추천
    @PostMapping("/exercise-place")
    public ResponseEntity<List<List<String>>> placeRecommend(@RequestBody SearchDTO searchDTO) {
        String prompt = String.format(
        		"%s %s에서 %s 운동을 하려고 합니다. 다음 조건에 따라 적절한 운동 장소에 대한 정보를 알려주세요.\n" + searchCondition,
                searchDTO.getWideArea(), searchDTO.getDetailArea(), searchDTO.getCategory());
        return getChatGptResponse(prompt);
    }
    
    
    // 운동 후 식사할 맛집 추천
    @PostMapping("/restaurant")
    public ResponseEntity<List<List<String>>> restaurantRecommend(@RequestBody SearchDTO searchDTO) {
        String prompt = String.format(
        		"%s %s에서 운동 후 단체로 맛집으로 회식가려고 합니다. %s 음식을 제공하는 식당에 대한 정보를 알려주세요.\n" + searchCondition,
                searchDTO.getWideArea(), searchDTO.getDetailArea(), searchDTO.getCategory());
        return getChatGptResponse(prompt);
    }
    
    
    private ResponseEntity<List<List<String>>> getChatGptResponse(String prompt) {
        try {
        	// 사용자의 요청을 ChatGPT API로 전달
            URL url = new URL(GPT_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            
            // 사용자 입력을 메시지로 구성
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);
            
            JSONArray messages = new JSONArray();
            messages.put(message);
            
            // ChatGPT API에 전송할 데이터 구성
            JSONObject data = new JSONObject();
            data.put("model", "gpt-3.5-turbo");
            data.put("temperature", 0.7);
            data.put("messages", messages);
            
            // API에 데이터를 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            bw.write(data.toString());
            bw.flush();
            bw.close();
            
            // API로부터의 응답을 읽음
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            
            String response = sb.toString();
            JSONObject jsonResponse = new JSONObject(response);
            String content = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

            // content 파싱하여 2차원 리스트로 변환
            List<List<String>> resultList = parseContentToList(content);

            
            // 응답을 클라이언트에 반환
            // - 타입 추론 : ResponseEntity<List>를 반환시 스프링이 JSON으로 자동 변환 
            return new ResponseEntity<>(resultList, HttpStatus.OK); 
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    // chatGPT의 응답 중 content 부분만 2차원 리스트로 파싱하는 메서드
    private List<List<String>> parseContentToList(String content) {
        content = content.replaceAll("[\\{\\}\\[\\]\"]", "").trim();
        String[] rows = content.split("\n");
        List<List<String>> resultList = new ArrayList<>();
        
        for (String row : rows) {
            String[] elements = row.split(",");
            List<String> rowList = new ArrayList<>();
            for (String element : elements) {
                rowList.add(element.trim());
            }
            resultList.add(rowList);
        }
        
        return resultList;
    }
    
    
    @Data
    public static class SearchDTO {
        private String wideArea;
        private String detailArea;
        private String category;
    }
}
