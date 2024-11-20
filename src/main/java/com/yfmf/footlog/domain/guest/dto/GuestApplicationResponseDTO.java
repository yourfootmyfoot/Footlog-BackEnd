package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.guest.entity.GuestApplication;
import com.yfmf.footlog.domain.guest.enums.ApplicationStatus;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.dto.MemberSimpleDTO;
import com.yfmf.footlog.domain.member.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "용병 신청 응답 데이터")
public class GuestApplicationResponseDTO {

    @Schema(description = "용병 신청 ID", example = "1")
    private Long id;

    @Schema(description = "모집글 ID", example = "100")
    private Long recruitmentId;

    @Schema(description = "신청자 정보", implementation = MemberSimpleDTO.class)
    private MemberSimpleDTO applicant;

    @Schema(description = "신청자가 지원한 포지션", example = "FORWARD")
    private Position applyPosition;

    @Schema(description = "신청 메시지", example = "용병으로 팀에 기여하고 싶습니다!")
    private String message;

    @Schema(description = "신청 상태", example = "PENDING")
    private ApplicationStatus status;

    @Schema(description = "신청 생성 날짜 및 시간", example = "2024-11-19T10:15:30")
    private LocalDateTime createdAt;

    public GuestApplicationResponseDTO(GuestApplication application, Member applicant) {
        this.id = application.getId();
        this.recruitmentId = application.getRecruitment().getId();
        this.applicant = new MemberSimpleDTO(applicant);
        this.applyPosition = application.getApplyPosition();
        this.message = application.getMessage();
        this.status = application.getStatus();
        this.createdAt = application.getCreatedAt();
    }
}