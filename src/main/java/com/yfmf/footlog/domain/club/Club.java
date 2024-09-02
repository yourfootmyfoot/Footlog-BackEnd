package com.yfmf.footlog.domain.club;

import com.yfmf.footlog.users.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_club")
public class Club {


    @Id
    @Column(name = "CLUB_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @ManyToOne
    @JoinColumn(name = "CLUB_OWNER")
    private User clubOwner;  //구단주
    private Long userId;

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

    @Column(name = "PEAK_DAYS") // 자주 운동하는 날
    @Enumerated(EnumType.STRING)
    private List<PeakDays> peakDays;  // 월~일

    public Club() {
    }

    public Club(User clubOwner,
                String clubName, String clubIntroduction,
                String clubCode, LocalDateTime erollDate,
                PeakHours peakHours, List<PeakDays> peakDays) {
        this.clubOwner = clubOwner;
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
                ", clubOwner=" + clubOwner +
                ", clubName='" + clubName + '\'' +
                ", clubIntroduction='" + clubIntroduction + '\'' +
                ", clubCode='" + clubCode + '\'' +
                ", erollDate=" + erollDate +
                ", peakHours=" + peakHours +
                ", peakDays=" + peakDays +
                '}';
    }
}
