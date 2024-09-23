package com.yfmf.footlog.domain.member.controller;

import com.yfmf.footlog.domain.member.dto.MemberResponseDTO;
import com.yfmf.footlog.domain.member.service.MemberSocialLoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "카카오 회원 API", description = "소셜 로그인 및 회원가입 기능을 제공하는 API")
public class MemberSocialLoginController {

    private final MemberSocialLoginService memberSocialLoginService;

    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=262c56061ee06d4004d2f9b94db133a4&redirect_uri=http://localhost:8080/api/auth/kakao/login
    // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=262c56061ee06d4004d2f9b94db133a4&redirect_uri=http://172.16.17.66:8181//api/auth/kakao/login
    /*
        카카오 로그인
     */
    @GetMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam(name = "code") String code, HttpServletResponse response, HttpServletRequest request) {
        // 로그인 후 토큰 발급
        MemberResponseDTO.authTokenDTO tokenDTO = memberSocialLoginService.kakaoLogin(code, request);

        // JWT Access Token을 HttpOnly 쿠키에 저장
        Cookie accessTokenCookie = new Cookie("accessToken", tokenDTO.accessToken());
        accessTokenCookie.setHttpOnly(true); // HttpOnly로 설정하여 클라이언트에서 접근하지 못하도록 설정
        accessTokenCookie.setMaxAge((int) (tokenDTO.accessTokenValidTime() / 1000));  // Access 토큰 유효 기간 설정 (초 단위)
        accessTokenCookie.setPath("/"); // 루트 경로에서 모든 페이지에서 접근 가능
        accessTokenCookie.setSecure(false); // HTTPS 환경에서는 true로 설정

        // JWT Refresh Token을 HttpOnly 쿠키에 저장
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDTO.refreshToken());
        refreshTokenCookie.setHttpOnly(true); // HttpOnly로 설정하여 클라이언트에서 접근하지 못하도록 설정
        refreshTokenCookie.setMaxAge((int) (tokenDTO.refreshTokenValidTime() / 1000)); // Refresh 토큰 유효 기간 설정 (초 단위)
        refreshTokenCookie.setPath("/"); // 모든 경로에서 접근 가능
        refreshTokenCookie.setSecure(false); // HTTPS 환경에서는 true로 설정

        // 응답에 쿠키 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);



        // 토큰을 받아오면 클라이언트 측에서 사용하도록 응답을 보내고
        // 여기서 적절하게 프론트엔드의 `/match`로 리다이렉트할 수 있게 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:3000/match");  // React 클라이언트에서 처리할 수 있도록 리다이렉트
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
