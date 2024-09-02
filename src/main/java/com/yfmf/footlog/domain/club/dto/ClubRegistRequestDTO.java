package com.yfmf.footlog.domain.club.dto;



import com.yfmf.footlog.domain.club.enums.PeakDays;
import com.yfmf.footlog.domain.club.enums.PeakHours;

import java.time.LocalDateTime;
import java.util.List;

public class ClubRegistRequestDTO {

    private Long userId;
    private String clubName;
    private String clubIntroduction;
    private String clubCode;
    private LocalDateTime erollDate;
    private PeakHours peakHours;
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
