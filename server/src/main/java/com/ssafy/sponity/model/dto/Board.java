package com.ssafy.sponity.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class Board {
    private int boardId;
    private int clubId;
    private String userId;
    private String nickname;
    private String title;
    private String content;
    private int viewCnt;
    private String regDate;
    private String img1;
    private String img2;
    private String img3;
    private List<Review> reviewList;
}