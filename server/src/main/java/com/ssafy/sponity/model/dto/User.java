package com.ssafy.sponity.model.dto;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String password;
    private String userName;
    private String nickname;
    private String email;
    private String wideArea;
    private String detailArea;
    private String profileImg;
}
