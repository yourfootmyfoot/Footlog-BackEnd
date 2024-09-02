package com.yfmf.footlog.domain.club.dto;



import com.yfmf.footlog.domain.club.enums.PeakDays;
import com.yfmf.footlog.domain.club.enums.PeakHours;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class ClubRegistRequestDTO {

    @Schema(description = "구단주의 userId", example = "1")
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

    @Schema(description = "자주 운동하는 시간대", example = "아침, 낮, 저녁, 심야 중 선택")
    @NotNull(message = "자주 운동하는 시간대를 선택해주세요.")
    private PeakHours peakHours;

    @Schema(description = "자주 운동하는 요일", example = "일")
    @NotNull(message = "자주 운동하는 요일을를 입력해주세요.")
    private List<PeakDays> peakDays;

    public ClubRegistRequestDTO() {
    }

    public ClubRegistRequestDTO(Long userId,
                                String clubName, String clubIntroduction,
                                String clubCode, LocalDateTime erollDate,
                                List<PeakDays> peakDays, PeakHours peakHours) {
        this.userId = userId;
        this.clubName = clubName;
        this.clubIntroduction = clubIntroduction;
        this.clubCode = clubCode;
        this.erollDate = erollDate;
        this.peakHours = peakHours;
        this.peakDays = peakDays;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubIntroduction() {
        return clubIntroduction;
    }

    public void setClubIntroduction(String clubIntroduction) {
        this.clubIntroduction = clubIntroduction;
    }

    public String getClubCode() {
        return clubCode;
    }

    public void setClubCode(String clubCode) {
        this.clubCode = clubCode;
    }

    public LocalDateTime getErollDate() {
        return erollDate;
    }

    public void setErollDate(LocalDateTime erollDate) {
        this.erollDate = erollDate;
    }

    public PeakHours getPeakHours() {
        return peakHours;
    }

    public void setPeakHours(PeakHours peakHours) {
        this.peakHours = peakHours;
    }

    public List<PeakDays> getPeakDays() {
        return peakDays;
    }

    public void setPeakDays(List<PeakDays> peakDays) {
        this.peakDays = peakDays;
    }

    @Override
    public String toString() {
        return "ClubRegistRequestDTO{" +
                ", clubOwner=" + userId +
                ", clubName='" + clubName + '\'' +
                ", clubIntroduction='" + clubIntroduction + '\'' +
                ", clubCode='" + clubCode + '\'' +
                ", erollDate=" + erollDate +
                ", peakHours=" + peakHours +
                ", peakDays=" + peakDays +
                '}';
    }
}
