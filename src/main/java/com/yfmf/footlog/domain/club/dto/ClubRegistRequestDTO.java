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
public class ClubRegistRequestDTO {

    @Schema(hidden = true)
    @NotNull(message = "구단주의 userId를 입력해주세요.")
    private Long userId;

    @Schema(description = "구단 이름", example = "FC서울")
    @NotNull(message = "등록할 구단 이름를 입력해주세요.")
    private String clubName;

    @Schema(description = "구단 소개글", example = "안녕하세요. FC서울입니다")
    @NotNull(message = "구단 소개글을 입력해주세요.")
    private String clubIntroduction;

    @Schema(description = "구단 코드", example = "FCSeoul")
    @NotNull(message = "구단 코드를 입력해주세요.")
    private String clubCode;

    @Schema(description = "등록 날짜")
    @NotNull(message = "구단 등록 날짜를 입력해주세요.")
    private LocalDateTime erollDate;

    @Schema(description = "자주 운동하는 요일", example = "[\"월\", \"화\"]")
    @NotNull(message = "자주 운동하는 요일을 입력해주세요.")
    private List<PeakDays> days;  // Enum 사용

    @Schema(description = "자주 운동하는 시간대", example = "[\"아침\", \"저녁\"]")
    @NotNull(message = "자주 운동하는 시간대를 입력해주세요.")
    private List<PeakHours> times;  // Enum 사용

    public ClubRegistRequestDTO() {
    }

    public ClubRegistRequestDTO(Long userId, String clubName, String clubIntroduction, String clubCode, LocalDateTime erollDate, List<PeakDays> days, List<PeakHours> times) {
        this.userId = userId;
        this.clubName = clubName;
        this.clubIntroduction = clubIntroduction;
        this.clubCode = clubCode;
        this.erollDate = erollDate;
        this.days = days;
        this.times = times;
    }

    @Override
    public String toString() {
        return "ClubRegistRequestDTO{" +
                ", clubOwner=" + userId +
                ", clubName='" + clubName + '\'' +
                ", clubIntroduction='" + clubIntroduction + '\'' +
                ", clubCode='" + clubCode + '\'' +
                ", erollDate=" + erollDate +
                ", peakHours=" + times +
                ", peakDays=" + days +
                '}';
    }
}
