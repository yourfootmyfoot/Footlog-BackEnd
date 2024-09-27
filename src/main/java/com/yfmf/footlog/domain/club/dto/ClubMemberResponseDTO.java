package com.yfmf.footlog.domain.club.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubMemberResponseDTO {

    @Schema(description = "사용자의 ID", example = "123")
    private Long userId;

    @Schema(description = "구단의 ID", example = "456")
    private Long clubId;

    @Schema(description = "구단의 이름", example = "FC서울")
    private String clubName;

    @Schema(description = "가입 또는 탈퇴 상태", example = "가입 성공")
    private String status;

    public ClubMemberResponseDTO(Long userId, Long clubId, String clubName, String status) {
        this.userId = userId;
        this.clubId = clubId;
        this.clubName = clubName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClubMemberResponseDTO{" +
                "userId=" + userId +
                ", clubId=" + clubId +
                ", clubName='" + clubName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}