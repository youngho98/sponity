package com.ssafy.sponity.model.dto;

import lombok.Data;

@Data
public class Member {
    private int memberId;
    private String userId;
    private int clubId;
    private String leader;
    private String fullMember;
}