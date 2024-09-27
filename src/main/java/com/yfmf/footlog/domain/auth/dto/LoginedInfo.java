package com.yfmf.footlog.domain.auth.dto;

import com.yfmf.footlog.domain.member.domain.Authority;
import lombok.Data;

@Data
public class LoginedInfo {
    private Long userId;
    private String name;
    private String email;
    private Authority authority;

    // 4개의 매개변수를 받는 생성자 추가
    public LoginedInfo(Long userId, String name, String email, Authority authority) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.authority = authority;
    }
}

