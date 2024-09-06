package com.yfmf.footlog.login.dto;

public interface OAuth2Response {

    // 제공자
    String getProvider();
    // 제공자 발급 아이디
    String getProviderId();
    // 이메일
    String getEmail();
    // 사용자 이름
    String getName();
}
