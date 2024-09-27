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

    // jwt 토큰 암호화를 위한 키
    private final Key secretKey;
    // Access token의 시간 : 15분
    private static final long ACCESS_TOKEN_LIFETIME = 15 * 60 * 1000L;
    // Refresh token의 시간 : 3일
    private static final long REFRESH_TOKEN_LIFETIME = 3 * 24 * 60 * 60 * 1000L;

    private final MemberRepository memberRepository;

    public JWTTokenProvider(@Value("${jwt.secret}") String secretKey, MemberRepository memberRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.memberRepository = memberRepository;
    }

    public MemberResponseDTO.authTokenDTO generateToken(Authentication authentication) {
        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User springUser =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            String email = springUser.getUsername();
            String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

            return generateToken(email, member.getId(), member.getName(), authentication.getAuthorities());
        }

        throw new RuntimeException("알 수 없는 principal 타입입니다.");
    }


    public MemberResponseDTO.authTokenDTO generateToken(String email, Long userId, String name, Collection<? extends GrantedAuthority> grantedAuthorities) {
        // 권한 확인
        String authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 현재 시간
        Date now = new Date();

        // Access 토큰 제작
        String accessToken = Jwts.builder()
                .setSubject(email)  // email을 subject로 설정
                .claim("userId", userId)  // userId 추가
                .claim("name", name)  // name 추가
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

        // 권한 정보가 없으면 예외 발생
        String authoritiesClaim = claims.get(AUTHORITIES_KEY) != null ? claims.get(AUTHORITIES_KEY).toString() : "";
        if (authoritiesClaim.isEmpty()) {
            throw new RuntimeException("권한 정보가 없는 Token 입니다.");
        }

        // 권한 정보가 단일한 경우 처리 (USER 또는 NONE만 허용)
        Authority authorityEnum;
        try {
            authorityEnum = Authority.valueOf(authoritiesClaim);
        } catch (IllegalArgumentException e) {
            log.error("권한 값이 Authority enum과 일치하지 않습니다: {}", authoritiesClaim);
            throw new RuntimeException("유효하지 않은 권한 값입니다.");
        }

        // JWT의 subject에서 email, claims에서 userId, name을 가져옴
        String email = claims.getSubject();  // email은 subject에 저장됨
        Long userId = claims.get("userId", Long.class);  // userId는 claim에서 가져옴
        String name = claims.get("name", String.class);  // name도 claim에서 가져옴


        // LoginedInfo 객체 생성
        LoginedInfo loginedInfo = new LoginedInfo(userId, name, email, authorityEnum);

        // authorities에 SimpleGrantedAuthority를 생성하여 UsernamePasswordAuthenticationToken 반환
        Collection<? extends GrantedAuthority> authorities =
                Arrays.asList(new SimpleGrantedAuthority(authoritiesClaim));


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
