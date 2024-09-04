package com.yfmf.footlog.login.jwt;

import com.yfmf.footlog.login.dto.AuthTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";

    // jwt 토큰 암호화용 키
    private final Key secret;

    // 엑세스 토큰: 15분
    private static final Long ACCESS_TOKEN_LIFETIME = 15 * 60 * 1000L;

    // 리프레시 토큰: 1일
    private static final Long REFRESH_TOKEN_LIFETIME = 60 * 60 * 1000L;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secret = Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthTokenDto generateToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        if (authorities.isEmpty()) {
            throw new RuntimeException("권한 정보가 없는 토큰은 생성할 수 없습니다.");
        }

        return generateToken(authentication.getName(), authentication.getAuthorities());
    }

    public AuthTokenDto generateToken(String username, Collection<? extends GrantedAuthority> grantedAuthorities) {

        // 권한 확인
        String authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        Date now = new Date();

        // 엑세스 토큰 생성
        String accessToken = Jwts.builder()
                // 아이디
                .setSubject(username)
                // 권한
                .claim(AUTHORITIES_KEY, authorities)
                .claim(CLAIM_TYPE, TYPE_ACCESS)
                // 토큰 발행 시간
                .setIssuedAt(now)
                // 만료 시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_LIFETIME))
                // 암호화
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();

        // 리프레시 토큰 생성
        String refreshToken = Jwts.builder()
                .claim(CLAIM_TYPE, TYPE_REFRESH)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_LIFETIME))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();

        return new AuthTokenDto("bearer", accessToken, ACCESS_TOKEN_LIFETIME, refreshToken, REFRESH_TOKEN_LIFETIME);
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException | ExpiredJwtException
                 | UnsupportedJwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    // jwt 토큰 복호화 -> 토큰 정보 확인
    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);

        // 토큰 권한 확인
        String authoritiesClaim = claims.get(AUTHORITIES_KEY) != null ? claims.get(AUTHORITIES_KEY).toString() : "";

        if (authoritiesClaim.isEmpty()) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        // 토큰 권한 정보 가져오기
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        // UserDetails 객체 생성 -> 이후 Authentication 객체 반환
        // 이 때 User는 Security의 User 객체 사용
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Claims parseClaims(String accessToken) {

        try {
            return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            return e.getClaims();
        }
    }

    public boolean isRefreshToken(String token) {
        String type = (String) Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().get(CLAIM_TYPE);
        return type.equals("refresh");
    }

    public String resolveToken(HttpServletRequest request) {

        String bearer = request.getHeader("Authorization");

        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }

        return null;
    }
}
