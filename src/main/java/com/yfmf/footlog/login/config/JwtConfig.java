package com.yfmf.footlog.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;
}
