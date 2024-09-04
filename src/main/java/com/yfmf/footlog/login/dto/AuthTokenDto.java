package com.yfmf.footlog.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthTokenDto {

    private String grantType;
    private String accessToken;
    private Long accessTokenValidTime;
    private String refreshToken;
    private Long refreshTokenValidTime;
}
