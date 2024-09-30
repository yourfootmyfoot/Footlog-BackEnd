package com.yfmf.footlog.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MemberRequestDTO {

    // 기본 회원 가입
    public record signUpDTO(
            @NotBlank(message = "이름을 입력해 주세요.")
            String name,
            @Email(message = "올바른 이메일 주소를 입력해 주세요.")
            @NotBlank(message = "이메일을 입력해 주세요.")
            String email,
            @Pattern(regexp = "^(?=.*\\d).{6,}$", message = "패스워드는 최소 6자 이상이어야 하며, 숫자를 포함해야 합니다.")
            String password,
            String confirmPassword,
            String gender
    ) {
    }

    // 기본 로그인
    public record loginDTO(
            @Schema(description = "이메일", example = "test@example.com")
            @Email(message = "올바른 이메일 주소를 입력해 주세요.")
            @NotBlank(message = "이메일을 입력해 주세요.")
            String email,

            @Schema(description = "비밀번호", example = "password123")
            String password
    ) {
    }
    
}
