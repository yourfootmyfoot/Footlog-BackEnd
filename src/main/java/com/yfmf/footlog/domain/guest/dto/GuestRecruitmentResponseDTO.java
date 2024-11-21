package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.club.dto.ClubSimpleDTO;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.guest.entity.GuestRecruitment;
import com.yfmf.footlog.domain.guest.enums.RecruitmentStatus;
import com.yfmf.footlog.domain.member.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "용병 모집 응답 DTO")
public class GuestRecruitmentResponseDTO {

    @Schema(description = "모집글 ID", example = "1", required = true)
    private Long id;

    @Schema(description = "모집글 제목", example = "같이 축구해요", required = true)
    private String title;

    @Schema(description = "작성자 이름(닉네임)", example = "송호진", required = true)
    private Long userId;

    @Schema(description = "구단 정보 (간략화)", required = true)
    private ClubSimpleDTO club;

    @Schema(description = "경기 날짜", example = "2024-12-25", required = true)
    private LocalDate matchDate;

    @Schema(description = "경기 시작 시간", example = "14:00", required = true)
    private LocalTime matchStartTime;

    @Schema(description = "경기 종료 시간", example = "16:00", required = true)
    private LocalTime matchEndTime;

    @Schema(description = "경기 장소", example = "서울월드컵경기장", required = true)
    private String location;

    @Schema(description = "필요 인원 수", example = "5", required = true)
    private Integer requiredNumber;

    @Schema(description = "필요 포지션 목록", example = "[\"FORWARD\", \"GOALKEEPER\"]", required = true)
    private List<Position> requiredPositions;

    @Schema(description = "용병비 (원)", example = "50000", required = true)
    private Integer pay;

    @Schema(description = "용병 모집 설명", example = "경험 많은 선수를 선호합니다.")
    private String description;

    @Schema(description = "모집 상태", example = "RECRUITING", required = true)
    private RecruitmentStatus status;

    @Schema(description = "모집글 생성 일시", example = "2024-11-20T10:00:00", required = true)
    private LocalDateTime createdAt;

    @Schema(description = "현재 신청자 수", example = "3", required = true)
    private int applicationCount;

    public GuestRecruitmentResponseDTO(GuestRecruitment recruitment, Club club) {
        this.id = recruitment.getId();
        this.club = new ClubSimpleDTO(club);
        this.matchDate = recruitment.getMatchDate();
        this.matchStartTime = recruitment.getMatchStartTime();
        this.matchEndTime = recruitment.getMatchEndTime();
        this.location = recruitment.getLocation();
        this.requiredNumber = recruitment.getRequiredNumber();
        this.requiredPositions = recruitment.getRequiredPositions();
        this.pay = recruitment.getPay();
        this.description = recruitment.getDescription();
        this.status = recruitment.getStatus();
        this.createdAt = recruitment.getCreatedAt();
    }
}