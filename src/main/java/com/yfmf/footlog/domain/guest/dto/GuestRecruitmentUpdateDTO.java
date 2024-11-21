package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestRecruitmentUpdateDTO {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotNull(message = "경기 날짜는 필수입니다")
    @Future(message = "경기 날짜는 현재보다 이후여야 합니다")
    private LocalDate matchDate;

    @NotBlank(message = "시작 시간은 필수입니다")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "올바른 시간 형식이 아닙니다")
    private String matchStartTime;

    @NotBlank(message = "종료 시간은 필수입니다")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "올바른 시간 형식이 아닙니다")
    private String matchEndTime;

    @NotBlank(message = "경기 장소는 필수입니다")
    private String location;

    @Min(value = 1, message = "최소 1명 이상의 인원이 필요합니다")
    private int requiredNumber;

    @NotEmpty(message = "필요 포지션을 최소 1개 이상 선택해주세요")
    private List<Position> requiredPositions;

    @Min(value = 0, message = "용병비는 0원 이상이어야 합니다")
    private int pay;

    @Size(max = 500, message = "추가 설명은 500자를 초과할 수 없습니다")
    private String description;

    @Builder
    public GuestRecruitmentUpdateDTO(String title, LocalDate matchDate,
                                     String matchStartTime, String matchEndTime, String location,
                                     int requiredNumber, List<Position> requiredPositions,
                                     int pay, String description) {
        this.title = title;
        this.matchDate = matchDate;
        this.matchStartTime = matchStartTime;
        this.matchEndTime = matchEndTime;
        this.location = location;
        this.requiredNumber = requiredNumber;
        this.requiredPositions = requiredPositions;
        this.pay = pay;
        this.description = description;
    }
}