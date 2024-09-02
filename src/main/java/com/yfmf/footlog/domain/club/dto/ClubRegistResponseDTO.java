package com.yfmf.footlog.domain.club.dto;

import com.yfmf.footlog.domain.club.enums.PeakDays;
import com.yfmf.footlog.domain.club.enums.PeakHours;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ClubRegistResponseDTO {
    @Schema(description = "구단주의 userId", example = "1")
    private Long userId;

    @Schema(description = "구단 이름", example = "FC서울")
    private String clubName;

    @Schema(description = "구단 소개글", example = "안녕하세요. FC서울입니다")
    private String clubIntroduction;

    @Schema(description = "구단 코드", example = "FCSeoul")
    private String clubCode;

    @Schema(description = "등록 날짜")
    private LocalDateTime erollDate;

    @Schema(description = "자주 운동하는 시간대", example = "아침, 낮, 저녁, 심야 중 선택")
    private PeakHours peakHours;

    @Schema(description = "자주 운동하는 요일", example = "일")
    private List<PeakDays> peakDays;

    // 기본 생성자
    public ClubRegistResponseDTO() {}

    // 모든 필드를 받는 생성자
    public ClubRegistResponseDTO(Long userId, String clubName, String clubIntroduction, String clubCode,
                                 LocalDateTime erollDate, PeakHours peakHours, List<PeakDays> peakDays) {
        this.userId = userId;
        this.clubName = clubName;
        this.clubIntroduction = clubIntroduction;
        this.clubCode = clubCode;
        this.erollDate = erollDate;
        this.peakHours = peakHours;
        this.peakDays = peakDays;
    }
}
