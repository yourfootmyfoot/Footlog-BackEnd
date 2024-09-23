package com.yfmf.footlog.domain.auth.jwt;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.member.domain.Authority;
import com.yfmf.footlog.domain.member.dto.MemberResponseDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTTokenProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";
    private static final String CLAIM_TYPE = "type";

    // jwt 토큰 암호화를 위한 키
    private final Key secretKey;
    // Access token의 시간 : 15분
    private static final long ACCESS_TOKEN_LIFETIME = 15 * 60 * 1000L;
    // Refresh token의 시간 : 3일
    private static final long REFRESH_TOKEN_LIFETIME = 3 * 24 * 60 * 60 * 1000L;

    public JWTTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public MemberResponseDTO.authTokenDTO generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        if (authorities.isEmpty()) {
            throw new RuntimeException("권한 정보가 없는 토큰을 생성할 수 없습니다.");
        }

        return generateToken(authentication.getName(), authentication.getAuthorities());
    }

    public MemberResponseDTO.authTokenDTO generateToken(String name, Collection<? extends GrantedAuthority> grantedAuthorities) {
        // 권한 확인
        String authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 현재 시간
        Date now = new Date();

        // Access 토큰 제작
        String accessToken = Jwts.builder()
                // 아이디 주입
                .setSubject(name)
                // 권한 주입
                .claim(AUTHORITIES_KEY, authorities)
                .claim(CLAIM_TYPE, TYPE_ACCESS)
                // 토큰 발행 시간 정보
                .setIssuedAt(now)
                // 만료시간 주입
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_LIFETIME))
                // 암호화
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // Refresh 토큰 제작
        String refreshToken = Jwts.builder()
                .claim(CLAIM_TYPE, TYPE_REFRESH)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_LIFETIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new MemberResponseDTO.authTokenDTO(BEARER_TYPE, accessToken, ACCESS_TOKEN_LIFETIME, refreshToken, REFRESH_TOKEN_LIFETIME);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.info("올바르지 않은 서명의 JWT Token 입니다.", e);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT Token 입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 형식의 JWT Token 입니다.", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Claims가 비어있습니다.", e);
        }
        return false;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT Token 입니다.", e);
            return e.getClaims();
        }
    }

    // JWT 토큰 복호화 -> 토큰 정보 확인
    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);

        // 권한 정보가 없으면 기본 권한을 할당하거나 예외 처리
        String authoritiesClaim = claims.get(AUTHORITIES_KEY) != null ? claims.get(AUTHORITIES_KEY).toString() : "";
        if (authoritiesClaim.isEmpty()) {
            throw new RuntimeException("권한 정보가 없는 Token 입니다.");
        }
        // 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // subject가 email인 경우 처리
        String subject = claims.getSubject();
        Long userId = null;
        String userName = claims.get("name", String.class); // JWT 토큰에 저장된 이름 정보 가져오기

        try {
            // subject가 Long 타입일 때 처리
            userId = Long.parseLong(subject);
        } catch (NumberFormatException e) {
            // subject가 이메일인 경우 처리
            log.warn("Subject is not a userId, treating as email: {}", subject);
        }

        // LoginedInfo 객체 생성
        LoginedInfo loginedInfo = new LoginedInfo();
        loginedInfo.setUserId(userId);  // userId가 null일 수 있음
        loginedInfo.setEmail(subject);  // 이메일이 subject로 저장됨
        loginedInfo.setName(userName);  // 이름 정보 저장
        loginedInfo.setAuthority(Authority.valueOf(claims.get(AUTHORITIES_KEY).toString()));

        return new UsernamePasswordAuthenticationToken(loginedInfo, "", authorities);
    }

    public boolean isRefreshToken(String token) {
        String type = (String) Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get(CLAIM_TYPE);
        return type.equals(TYPE_REFRESH);
    }

    public String resolveToken(HttpServletRequest request, String tokenType) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (tokenType.equals("accessToken") && cookie.getName().equals("accessToken")) {
                    return cookie.getValue();
                } else if (tokenType.equals("refreshToken") && cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
