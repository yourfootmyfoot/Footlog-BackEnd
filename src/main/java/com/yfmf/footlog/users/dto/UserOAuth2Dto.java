package com.yfmf.footlog.users.dto;

import lombok.Data;

@Data
public class UserOAuth2Dto {

    private String role;
    private String socialId;
    private String username;
}
