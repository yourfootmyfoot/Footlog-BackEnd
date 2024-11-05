package com.yfmf.footlog.domain.club.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubMemberResponseDTO {

    @Schema(description = "사용자의 ID", example = "123")
    private Long userId;

    @Schema(description = "구단의 ID", example = "456")
    private Long clubId;

    @Schema(description = "구단의 이름", example = "FC서울")
    private String clubName;

    @Schema(description = "사용자의 이름", example = "송호진")
    private String username;

    @Schema(description = "사용자의 등급", example = "송호진")
    private String role;

    @Schema(description = "가입 또는 탈퇴 상태", example = "OWNER, MANAGER")
    private String status;

    @Override
    public String toString() {
        return "ClubMemberResponseDTO{" +
                "userId=" + userId +
                ", clubId=" + clubId +
                ", clubName='" + clubName + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}