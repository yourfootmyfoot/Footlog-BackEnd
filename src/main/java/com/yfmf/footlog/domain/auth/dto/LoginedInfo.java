package com.yfmf.footlog.domain.auth.dto;

import com.yfmf.footlog.users.UserRole;
import lombok.Data;

@Data
public class LoginedInfo {
    private String userName;
    private UserRole role;
}
