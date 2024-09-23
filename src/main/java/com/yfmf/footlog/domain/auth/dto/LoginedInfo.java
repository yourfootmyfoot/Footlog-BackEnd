package com.yfmf.footlog.domain.auth.dto;

import com.yfmf.footlog.domain.member.domain.Authority;
import lombok.Data;

@Data
public class LoginedInfo {
    private Long userId;
    private String name;
    private String email;
    private Authority authority;
}

