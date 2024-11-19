package com.yfmf.footlog.login.dto;

import com.yfmf.footlog.login.entity.Refresh;
import lombok.Data;

@Data
public class RefreshDto {

    private String username;
    private String refresh;
    private String expiration;

    public Refresh toEntity() {
        return Refresh.builder()
                .username(username)
                .refresh(refresh)
                .expiration(expiration)
                .build();
    }
}
