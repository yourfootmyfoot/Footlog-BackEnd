package com.yfmf.footlog.domain.member.service;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.jwt.JWTTokenProvider;
import com.yfmf.footlog.domain.auth.refreshToken.domain.RefreshToken;
import com.yfmf.footlog.domain.auth.refreshToken.repository.RefreshTokenRepository;
import com.yfmf.footlog.domain.auth.utils.ClientUtils;
import com.yfmf.footlog.domain.member.domain.Authority;
import com.yfmf.footlog.domain.member.domain.Gender;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.domain.SocialType;
import com.yfmf.footlog.domain.member.dto.MemberRequestDTO;
import com.yfmf.footlog.domain.member.dto.MemberResponseDTO;
import com.yfmf.footlog.domain.member.repository.MemberRepository;
import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = false)
@RequiredArgsConstructor
@Service
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTTokenProvider jwtTokenProvider;

    /*
        기본 회원 가입
     */
    @Transactional
    public void signUp(MemberRequestDTO.signUpDTO requestDTO) {


        // 비밀번호 확인
        checkValidPassword(requestDTO.password(), passwordEncoder.encode(requestDTO.confirmPassword()));

        // 회원 생성
        Member member = newMember(requestDTO);

        // 회원 저장
        memberRepository.save(member);

    }

    /**
       기본 로그인 - 쿠키에 토큰 저장
    */
    public MemberResponseDTO.authTokenDTO login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MemberRequestDTO.loginDTO requestDTO) {

        // 1. 이메일 확인
        Member member = findMemberByEmail(requestDTO.email())
                .orElseThrow(() -> new ApplicationException(ErrorCode.EMPTY_EMAIL_MEMBER));

        // 2. 비밀번호 확인
        checkValidPassword(requestDTO.password(), member.getPassword());

        // 3. 토큰 발급 및 쿠키에 저장
        MemberResponseDTO.authTokenDTO authTokenDTO = getAuthTokenDTO(requestDTO.email(), requestDTO.password(), httpServletRequest);
        addTokenToCookie(httpServletResponse, "accessToken", authTokenDTO.accessToken());
        addTokenToCookie(httpServletResponse, "refreshToken", authTokenDTO.refreshToken());

        return authTokenDTO;
    }

    // 쿠키에 토큰 추가
    private void addTokenToCookie(HttpServletResponse response, String tokenName, String tokenValue) {
        Cookie cookie = new Cookie(tokenName, tokenValue);
        cookie.setHttpOnly(true);  // JavaScript에서 쿠키 접근을 차단
        cookie.setSecure(false);   // HTTPS에서만 전송되도록 설정 (필요 시 true로 설정)
        cookie.setPath("/");       // 애플리케이션의 모든 경로에서 쿠키가 유효하도록 설정
        cookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 만료 시간 설정 (1주일)

        response.addCookie(cookie);
    }

    // 비밀번호 확인
    private void checkValidPassword(String rawPassword, String encodedPassword) {

        log.info("{} {}", rawPassword, encodedPassword);

        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }
    }

    protected Optional<Member> findMemberByEmail(String email) {
        log.info("회원 확인 : {}", email);

        return memberRepository.findByEmail(email);
    }

    // 회원 생성
    protected Member newMember(MemberRequestDTO.signUpDTO requestDTO) {
        return Member.builder()
                .name(requestDTO.name())
                .email(requestDTO.email())
                .password(passwordEncoder.encode(requestDTO.password()))
                .gender(Gender.fromString(requestDTO.gender()))
                .socialType(SocialType.NONE)
                .authority(Authority.USER)
                .build();
    }

    // 토큰 발급
    protected MemberResponseDTO.authTokenDTO getAuthTokenDTO(String email, String password, HttpServletRequest httpServletRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(email, password);
        AuthenticationManager manager = authenticationManagerBuilder.getObject();
        Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);

        // 회원 정보 조회 후 LoginedInfo 생성
        Member member = findMemberByEmail(email).orElseThrow(() -> new ApplicationException(ErrorCode.EMPTY_EMAIL_MEMBER));
        LoginedInfo loginedInfo = new LoginedInfo(member.getId(), member.getName(), member.getEmail(), member.getAuthority());

        // 인증 객체에서 LoginedInfo로 교체
        Authentication newAuth = new UsernamePasswordAuthenticationToken(loginedInfo, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return jwtTokenProvider.generateToken(loginedInfo.getEmail(), loginedInfo.getUserId(), loginedInfo.getName(), authentication.getAuthorities());
    }


    // 토큰 재발급
    public MemberResponseDTO.authTokenDTO reissueToken(HttpServletRequest httpServletRequest) {

        // Request Header 에서 JWT Token 추출
        String token = jwtTokenProvider.resolveToken(httpServletRequest, "accessToken");

        // 토큰 유효성 검사
        if(token == null || !jwtTokenProvider.validateToken(token)) {
            throw new ApplicationException(ErrorCode.FAILED_VALIDATE_ACCESS_TOKEN);
        }

        // type 확인
        if(!jwtTokenProvider.isRefreshToken(token)) {
            throw new ApplicationException(ErrorCode.IS_NOT_REFRESH_TOKEN);
        }

        // RefreshToken
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByRefreshToken(token);

        if(refreshToken.isEmpty()) {
            throw new ApplicationException(ErrorCode.FAILED_GET_RERFRESH_TOKEN);
        }

        // 최초 로그인한 ip와 같은지 확인
        String currentIp = ClientUtils.getClientIp(httpServletRequest);
        if(!currentIp.equals(refreshToken.get().getIp())) {
            throw new ApplicationException(ErrorCode.DIFFERENT_IP_ADDRESS);
        }

        // 저장된 RefreshToken 정보를 기반으로 JWT Token 생성
        RefreshToken refreshTokenEntity = refreshToken.get();
        String email = refreshTokenEntity.getUserName();
        Long userId = refreshTokenEntity.getId();  // 적절한 userId 추출 방식
        String name = ""; // name 정보를 refresh token에서 찾을 수 없다면 적절히 처리
        Authority authority = refreshTokenEntity.getAuthorities();


        // JWT 토큰 발급
        MemberResponseDTO.authTokenDTO authTokenDTO = jwtTokenProvider.generateToken(
                email, userId, name, Collections.singletonList(new SimpleGrantedAuthority(authority.name()))
        );

        // RefreshToken Update
        refreshTokenRepository.save(RefreshToken.builder()
                        .ip(currentIp) // IP 주소를 업데이트
                        .authorities(refreshToken.get().getAuthorities())
                        .refreshToken(authTokenDTO.refreshToken())
                .build());

        return authTokenDTO;
    }

    /*
        로그아웃
     */
    public void logout(HttpServletRequest httpServletRequest) {
        
        log.info("로그아웃 - Refresh Token 확인");

        String token = jwtTokenProvider.resolveToken(httpServletRequest, "refreshToken");

        if(token == null || !jwtTokenProvider.validateToken(token)) {
            throw new ApplicationException(ErrorCode.FAILED_VALIDATE__REFRESH_TOKEN);
        }

        // Refresh Token 확인
        if (!jwtTokenProvider.isRefreshToken(token)) {
            throw new ApplicationException(ErrorCode.IS_NOT_REFRESH_TOKEN);
        }

        // RefreshToken 조회 및 null 체크
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> {
                    log.error("Refresh Token 을 얻을 수 없습니다. 토큰: {}", token);
                    return new ApplicationException(ErrorCode.FAILED_GET_RERFRESH_TOKEN);
                });

        // RefreshToken 삭제
        refreshTokenRepository.delete(refreshToken);
        log.info("로그아웃 성공");
    }


}
