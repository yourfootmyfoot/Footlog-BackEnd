package com.yfmf.footlog.login.service;

import com.yfmf.footlog.login.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;

    public ResponseEntity<?> tokenReissue(HttpServletRequest request, HttpServletResponse response) {

        // 리프레시 토큰 가져오기
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("리프레시 토큰이 없습니다.", BAD_REQUEST);
        }

        // 만료 체크
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("리프레시 토큰이 만료되었습니다.", BAD_REQUEST);
        }

        // 토큰이 리프레시인지 확인
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return new ResponseEntity<>("유효한 리프레시 토큰이 아닙니다.", BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // 새로운 토큰 발급
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
//        String newRefresh = jwtUtil.createJwt("refresh", username, role, 600000L);

        response.setHeader("access", newAccess);
//        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(OK);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
