package com.yfmf.footlog.domain.member.controller;


import com.yfmf.footlog.domain.auth.utils.ApiUtils;
import com.yfmf.footlog.domain.member.dto.MemberRequestDTO;
import com.yfmf.footlog.domain.member.dto.MemberResponseDTO;
import com.yfmf.footlog.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "회원 인증 API", description = "회원 가입, 로그인, 토큰 재발급, 로그아웃 기능을 제공하는 API")
public class MemberController {

    private final MemberService memberService;

    /*
          기본 회원 가입
       */
    @Operation(summary = "회원 가입", description = "회원 가입을 처리합니다.")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberRequestDTO.signUpDTO requestDTO) {

        memberService.signUp(requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }


    /*
         기본 로그인
      */
    @Operation(summary = "로그인", description = "회원 로그인을 처리하고 인증 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest httpServletRequest, @Valid @RequestBody MemberRequestDTO.loginDTO requestDTO) {

        MemberResponseDTO.authTokenDTO responseDTO = memberService.login(httpServletRequest, requestDTO);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
       Access Token 재발급 - Refresh Token 필요
    */
    @Operation(summary = "토큰 재발급", description = "Refresh Token을 사용하여 Access Token을 재발급합니다.")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissueToken(HttpServletRequest httpServletRequest) {

        MemberResponseDTO.authTokenDTO responseDTO = memberService.reissueToken(httpServletRequest);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    /*
        로그아웃 - Refresh Token 필요
     */
    @Operation(summary = "로그아웃", description = "Refresh Token을 사용하여 로그아웃을 처리합니다.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("로그아웃 시도");

        // 로그아웃 처리 (Refresh Token 확인 및 제거)
        memberService.logout(httpServletRequest);

        // Access Token 쿠키 삭제
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(0);  // 쿠키 만료
        accessTokenCookie.setPath("/");

        // Refresh Token 쿠키 삭제
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(0);  // 쿠키 만료
        refreshTokenCookie.setPath("/");

        // 쿠키 응답에 추가
        httpServletResponse.addCookie(accessTokenCookie);
        httpServletResponse.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
