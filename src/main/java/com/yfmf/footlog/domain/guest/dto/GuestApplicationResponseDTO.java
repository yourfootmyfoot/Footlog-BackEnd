package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.guest.entity.GuestApplication;
import com.yfmf.footlog.domain.guest.enums.ApplicationStatus;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.dto.MemberSimpleDTO;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestApplicationResponseDTO {
    private Long id;
    private Long recruitmentId;
    private MemberSimpleDTO applicant;
    private Position applyPosition;
    private String message;
    private ApplicationStatus status;
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