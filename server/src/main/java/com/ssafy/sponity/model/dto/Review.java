package com.ssafy.sponity.model.dto;

import lombok.Data;

@Data
public class Review {
    private int reviewId;
    private int boardId;
    private String userId;
    private String nickname;
    private String content;
    private String regDate;
}