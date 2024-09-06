package com.yfmf.footlog.users.dto;

import com.yfmf.footlog.users.UserRole;
import com.yfmf.footlog.users.entity.User;
import lombok.Data;

@Data
public class UserOAuth2Dto {

    private String role;
    private String socialId;
    private String username;

    public User toEntity() {
        return User.builder()
                .socialId(socialId)
                .userName(username)
                .role(UserRole.USER)
                .build();
    }
}
