package com.yfmf.footlog.domain.club.entity;

import com.yfmf.footlog.domain.club.enums.PeakDays;
import com.yfmf.footlog.domain.club.enums.PeakHours;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_club")
@Setter
@Getter
public class Club {


    @Id
    @Column(name = "CLUB_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(name = "CLUB_OWNER")
    private Long userId; //구단주

    @Column(name = "CLUB_NAME")
    private String clubName;  //구단이름

    @Column(name = "CLUB_INTRODUCTION")
    private String clubIntroduction;  //구단소개

    @Column(name = "CLUB_CODE")
    private String clubCode;  //구단코드

    @Column(name = "EROLL_DATE")
    private LocalDateTime erollDate;  //구단등록일

    @Column(name = "PEAK_HOURS")
    @Enumerated(EnumType.STRING)
    private PeakHours peakHours;  //구단활동시간대 아침,찾,저녁,심야


    @ElementCollection  // 이는 자주 운동하는 날을 별도의 테이블에 저장하고, 해당 테이블이 Club 엔티티와 연관되도록 합니다.
    @CollectionTable(name = "tbl_club_peak_days", joinColumns = @JoinColumn(name = "CLUB_ID"))
    @Column(name = "PEAK_DAYS") // 자주 운동하는 날
    @Enumerated(EnumType.STRING)
    private List<PeakDays> peakDays;  // 월~일

    public Club() {
    }

    public Club(Long userId,
                String clubName, String clubIntroduction,
                String clubCode, LocalDateTime erollDate,
                PeakHours peakHours, List<PeakDays> peakDays) {
        this.userId = userId;
        this.clubName = clubName;
        this.clubIntroduction = clubIntroduction;
        this.clubCode = clubCode;
        this.erollDate = erollDate;
        this.peakHours = peakHours;
        this.peakDays = peakDays;
    }

    @Override
    public String toString() {
        return "Club{" +
                "clubId=" + clubId +
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
