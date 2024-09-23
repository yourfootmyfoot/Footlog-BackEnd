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

    @ElementCollection
    @CollectionTable(name = "tbl_club_days", joinColumns = @JoinColumn(name = "CLUB_ID"))
    @Column(name = "DAYS")
    @Enumerated(EnumType.STRING)
    private List<PeakDays> days;  // 자주 운동하는 요일 (Enum)

    @ElementCollection
    @CollectionTable(name = "tbl_club_times", joinColumns = @JoinColumn(name = "CLUB_ID"))
    @Column(name = "TIMES")
    @Enumerated(EnumType.STRING)
    private List<PeakHours> times;  // 자주 운동하는 시간대 (Enum)

    public Club() {
    }

    public Club(Long userId, String clubName, String clubIntroduction, String clubCode, LocalDateTime erollDate, List<PeakDays> days, List<PeakHours> times) {
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
        return "Club{" +
                "clubId=" + clubId +
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
