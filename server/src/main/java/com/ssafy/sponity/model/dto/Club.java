package com.ssafy.sponity.model.dto;

import lombok.Data;

@Data
public class Club {
    private int clubId;
    private String clubName;
    private String category;
    private String wideArea;
    private String detailArea;
    private String introduction;
    private String clubImg;
    private String deleteYN;
    private int memberNum;
    private int likeNum;
}