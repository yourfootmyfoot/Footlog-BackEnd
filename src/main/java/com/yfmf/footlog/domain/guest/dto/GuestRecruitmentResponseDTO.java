package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.club.dto.ClubSimpleDTO;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.guest.entity.GuestRecruitment;
import com.yfmf.footlog.domain.guest.enums.RecruitmentStatus;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestRecruitmentResponseDTO {
    private Long id;
    private ClubSimpleDTO club;
    private LocalDateTime matchDateTime;
    private String location;
    private Integer requiredNumber;
    private List<Position> requiredPositions;
    private Integer pay;
    private String description;
    private RecruitmentStatus status;
    private LocalDateTime createdAt;
    private int applicationCount;  // 현재 신청자 수

    public GuestRecruitmentResponseDTO(GuestRecruitment recruitment, Club club) {
        this.id = recruitment.getId();
        this.club = new ClubSimpleDTO(club);
        this.matchDateTime = recruitment.getMatchDateTime();
        this.location = recruitment.getLocation();
        this.requiredNumber = recruitment.getRequiredNumber();
        this.requiredPositions = recruitment.getRequiredPositions();
        this.pay = recruitment.getPay();
        this.description = recruitment.getDescription();
        this.status = recruitment.getStatus();
        this.createdAt = recruitment.getCreatedAt();
    }
}