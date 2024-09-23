package com.yfmf.footlog.domain.auth.controller;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.jwt.JWTTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 로그인 상태 확인 API
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> checkLoginStatus(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request, "accessToken");  // 헤더 또는 쿠키에서 토큰을 추출

        Map<String, Object> response = new HashMap<>();

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            LoginedInfo loginedInfo = (LoginedInfo) authentication.getPrincipal();

            response.put("isLoggedIn", true);
            response.put("email", loginedInfo.getEmail());  // 이메일을 반환
            response.put("authority", loginedInfo.getAuthority());  // 사용자 권한 반환
        } else {
            response.put("isLoggedIn", false);
        }

        return ResponseEntity.ok(response);
    }
}
