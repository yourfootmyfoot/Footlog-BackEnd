package com.yfmf.footlog.login.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        // req에서 jwt token 추출
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String requestURI = ((HttpServletRequest) req).getRequestURI();

        // 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            if (!requestURI.equals("/api/auth/reissue") && !requestURI.equals("/api/auth/logout")) {

                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("{} 로그인 성공", authentication.getName());
            }
        }

        chain.doFilter(req, res);
    }
}
