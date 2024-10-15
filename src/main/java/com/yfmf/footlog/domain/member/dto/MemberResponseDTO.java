package com.yfmf.footlog.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yfmf.footlog.domain.member.domain.Authority;
import com.yfmf.footlog.domain.member.domain.Gender;

import java.time.LocalDateTime;

public class MemberResponseDTO {

    // 회원 정보 응답 DTO
    public static class MemberInfoDTO {
        private Long id;
        private String name;
        private String email;
        private Gender gender;
        private Authority authority;

        // 모든 필드를 포함하는 생성자가 필요
        public MemberInfoDTO(Long id, String name, String email, Gender gender, Authority authority) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.gender = gender;
            this.authority = authority;
        }
    }

    // 토큰 발급
    public record authTokenDTO(
            String grantType,
            String accessToken,
            Long accessTokenValidTime,
            String refreshToken,
            Long refreshTokenValidTime,
            Long userId // 추가: userId를 포함하여 Redis에 저장 시 참조 가능하도록 함
    ) {
    }

    public record KakaoProfile(
            String nickname,
            String email,
            String gender
    ){
    }

    // Kakao Token
    public record KakaoTokenDTO(
            @JsonProperty("token_type")
            String tokenType,
            @JsonProperty("access_token")
            String accessToken,
            @JsonProperty("refresh_token")
            String refreshToken,
            @JsonProperty("id_token")
            String idToken,
            @JsonProperty("expires_in")
            int expiresIn,
            @JsonProperty("refresh_token_expires_in")
            int refreshTokenExpiresIn,
            String scope
    ) {
    }

    // Kakao Info
    public record KakaoInfoDTO(
            long id,
            @JsonProperty("has_signed_up")
            boolean hasSignedUp,
            @JsonProperty("connected_at")
            LocalDateTime connectedAt,
            KakaoProperties properties,
            @JsonProperty("kakao_account")
            KakaoAccount kakaoAccount
    ) {
        public record KakaoProperties(
                String nickname
        ) {
        }

        public record KakaoAccount(
                @JsonProperty("profile_nickname_needs_agreement")
                boolean profileNicknameNeedsAgreement,
                String nickname,
                @JsonProperty("email_needs_agreement")
                boolean emailNeedsAgreement,
                @JsonProperty("is_email_valid")
                boolean isEmailValid,
                @JsonProperty("is_email_verified")
                boolean isEmailVerified,
                String email,
                @JsonProperty("age_range_needs_agreement")
                boolean ageRangeNeedsAgreement,
                @JsonProperty("age_range")
                String age_range,
                @JsonProperty("gender_needs_agreement")
                boolean genderNeedsAgreement,
                String gender
        ) {
        }
    }
}
