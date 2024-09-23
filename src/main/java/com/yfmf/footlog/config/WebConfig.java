package com.yfmf.footlog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // React 앱이 돌아가는 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)  // 쿠키를 포함하는 요청 허용
                .allowedHeaders("*")
                .exposedHeaders("Authorization");
    }
}

