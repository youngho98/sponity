package com.ssafy.sponity.model.dto;

import lombok.Data;

@Data
public class ClubLike {
    private int likeId;
    private String userId;
    private int clubId;
}