package com.yfmf.footlog.domain.auth.jwt;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.member.domain.Authority;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.dto.MemberResponseDTO;
import com.yfmf.footlog.domain.member.repository.MemberRepository;
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

    private static final long ACCESS_TOKEN_LIFETIME = 60 * 60 * 1000L; // 60 분
    private static final long REFRESH_TOKEN_LIFETIME = 3 * 24 * 60 * 60 * 1000L; // 3 days

    private final Key secretKey;

    public JWTTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * 권한 정보를 추출하여 문자열로 변환합니다.
     *
     * @param grantedAuthorities 권한 컬렉션
     * @return 권한 문자열 (예: ROLE_USER,ROLE_ADMIN)
     */
    private String extractAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(authority -> {
                    String role = authority.getAuthority();
                    if (!role.startsWith("ROLE_")) {
                        return "ROLE_" + role; // ROLE_ 접두사 없을 때만 추가
                    }
                    return role; // 이미 ROLE_가 있을 경우 그대로 반환
                })
                .collect(Collectors.joining(","));
    }


    /**
     * AccessToken과 RefreshToken을 생성합니다.
     *
     * @param email              사용자 이메일
     * @param userId             사용자 ID
     * @param name               사용자 이름
     * @param grantedAuthorities 권한 컬렉션
     * @return 생성된 토큰 정보
     */
    public MemberResponseDTO.authTokenDTO generateToken(String email, Long userId, String name, Collection<? extends GrantedAuthority> grantedAuthorities) {
        String authorities = extractAuthorities(grantedAuthorities);
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("name", name)
                .claim(AUTHORITIES_KEY, authorities)
                .claim(CLAIM_TYPE, TYPE_ACCESS)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_LIFETIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .claim(CLAIM_TYPE, TYPE_REFRESH)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_LIFETIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // userId를 추가하여 반환
        return new MemberResponseDTO.authTokenDTO(
                BEARER_TYPE,
                accessToken,
                ACCESS_TOKEN_LIFETIME,
                refreshToken,
                REFRESH_TOKEN_LIFETIME,
                userId // userId 전달
        );
    }

    /**
     * JWT 토큰의 유효성을 검증합니다.
     *
     * @param token 검증할 토큰
     * @return 유효한 경우 true, 그렇지 않으면 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("JWT Token 검증 실패: {}", e.getMessage());
        }
        return false;
    }

    /**
     * JWT 토큰의 Claims를 파싱합니다.
     *
     * @param accessToken 파싱할 토큰
     * @return 파싱된 Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT Token 입니다.", e);
            return e.getClaims();
        }
    }

    /**
     * JWT 토큰에서 인증 정보를 생성합니다.
     *
     * @param token 토큰
     * @return 인증 정보
     */
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        String authoritiesClaim = claims.get(AUTHORITIES_KEY, String.class);
        if (authoritiesClaim == null || authoritiesClaim.isEmpty()) {
            throw new RuntimeException("권한 정보가 없는 Token 입니다.");
        }

        // authoritiesClaim에서 권한을 추출하고 SimpleGrantedAuthority로 변환
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(authoritiesClaim.split(","))
                .map(SimpleGrantedAuthority::new) // SimpleGrantedAuthority에 권한 추가
                .collect(Collectors.toList());

        String email = claims.getSubject();
        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);
        Authority authority = claims.get(AUTHORITIES_KEY, Authority.class);

        LoginedInfo loginedInfo = new LoginedInfo(userId, name, email, authority); // 기본 권한 부여

        return new UsernamePasswordAuthenticationToken(loginedInfo, "", authorities);
    }

    /**
     * HttpServletRequest Access Token 또는 Refresh Token을 추출합니다.
     *
     * @param request   HTTP 요청 객체
     * @param tokenType 추출할 토큰 타입 ("accessToken" 또는 "refreshToken")
     * @return 추출된 토큰 값
     */
    public String resolveToken(HttpServletRequest request, String tokenType) {
        if ("accessToken".equals(tokenType)) {
            // Access Token은 헤더에서 가져옴
            return resolveAccessTokenFromHeader(request);
        } else if ("refreshToken".equals(tokenType)) {
            // Refresh Token은 쿠키에서 가져옴
            return resolveRefreshTokenFromCookies(request);
        }
        return null;
    }


    /**
     * 토큰에서 사용자 ID를 추출합니다.
     *
     * @param token 토큰
     * @return 사용자 ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseClaims(token); // 토큰을 파싱하여 Claims 추출
        return claims.get("userId", Long.class); // Claims에서 userId 추출
    }

    /**
     * HttpServletRequest 헤더에서 Access Token을 추출합니다.
     *
     * @param request HTTP 요청 객체
     * @return 추출된 Access Token 값
     */
    private String resolveAccessTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            log.info("Access Token 추출 성공: {}", bearerToken.substring(7));  // 디버그용 로그 추가
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 값만 추출
        } else {
            log.warn("Authorization 헤더에 Access Token이 없습니다.");
        }
        return null;
    }

    /**
     * HttpServletRequest 쿠키에서 Refresh Token을 추출합니다.
     *
     * @param request HTTP 요청 객체
     * @return 추출된 Refresh Token 값
     */
    private String resolveRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    log.info("Refresh Token 추출 성공: {}", cookie.getValue());  // 디버그용 로그 추가
                    return cookie.getValue();
                }
            }
        }
        log.warn("쿠키에 Refresh Token이 없습니다.");
        return null;
    }

    /**
     * 토큰이 Refresh Token인지 확인합니다.
     *
     * @param token 확인할 토큰
     * @return Refresh Token인 경우 true, 그렇지 않으면 false
     */
    public boolean isRefreshToken(String token) {
        return TYPE_REFRESH.equals(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get(CLAIM_TYPE));
    }
}