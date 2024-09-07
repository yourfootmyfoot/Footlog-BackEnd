package com.yfmf.footlog.login.jwt.filter;

import com.yfmf.footlog.login.dto.CustomOAuth2User;
import com.yfmf.footlog.login.jwt.util.JwtUtil;
import com.yfmf.footlog.users.dto.UserOAuth2Dto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/login(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 쿠키들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {

                authorization = cookie.getValue();
            }
        }

        // Authorization 헤더 검증
        if (authorization == null) {

            log.info("Authorization cookie not found");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메서드 종료
            return;
        }

        // 토큰
        String token = authorization;

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            log.info("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        // 토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // userDto를 생성해서 값을 세팅
        UserOAuth2Dto userDto = new UserOAuth2Dto();
        userDto.setUsername(username);
        userDto.setRole(role);

        // UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
