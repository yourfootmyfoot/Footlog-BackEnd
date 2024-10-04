package com.yfmf.footlog.domain.club.entity;

import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.club.enums.PeakDays;
import com.yfmf.footlog.domain.club.enums.PeakHours;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_club")
@Getter
@Setter
public class Club extends BaseTimeEntity {

    @Id
    @Column(name = "CLUB_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(name = "CLUB_OWNER_ID")
    private Long userId; //구단주

    @Column(name = "CLUB_NAME")
    private String clubName;  //구단이름

    @Column(name = "CLUB_INTRODUCTION")
    private String clubIntroduction;  //구단소개

    @Column(name = "CLUB_CODE", unique = true)
    private String clubCode;  //구단코드

    @Column(name = "EROLL_DATE")
    private LocalDateTime enrollDate;  //구단등록일

    // 구단원 수를 자동으로 관리하는 필드
    @Column(name = "MEMBER_COUNT", nullable = false)
    private int memberCount = 1;  // 기본값을 1로 설정

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

    @Column(nullable = false)
    private String skillLevel;  // 실력 등급 (예: 입문자, 아마추어 등)

    @Column(name = "AGE_GROUP") // 나이대 필드
    private String ageGroup;

    @Column(name = "GENDER") // 성별 필드
    private String gender;

    // Location 정보 추가
    @Column(name = "STADIUM_NAME")
    private String stadiumName;  // 운동하는 경기장 이름

    @Column(name = "CITY")
    private String city;  // 도시명

    @Column(name = "REGION")
    private String region;  // 지역명

    public Club() {
    }

    public Club(Long userId, String clubName, String clubIntroduction, String clubCode, LocalDateTime enrollDate,
                int memberCount, List<PeakDays> days, List<PeakHours> times, String skillLevel, String stadiumName,
                String city, String region, String ageGroup, String gender) {
        this.userId = userId;
        this.clubName = clubName;
        this.clubIntroduction = clubIntroduction;
        this.clubCode = clubCode;
        this.enrollDate = enrollDate;
        this.memberCount = memberCount;
        this.days = days;
        this.times = times;
        this.skillLevel = skillLevel;
        this.stadiumName = stadiumName;
        this.city = city;
        this.region = region;
        this.ageGroup = ageGroup;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Club{" +
                "clubId=" + clubId +
                ", clubOwner=" + userId +
                ", clubName='" + clubName + '\'' +
                ", clubIntroduction='" + clubIntroduction + '\'' +
                ", clubCode='" + clubCode + '\'' +
                ", erollDate=" + enrollDate +
                ", memberCount=" + memberCount +
                ", peakHours=" + times +
                ", peakDays=" + days +
                ", skillLevel=" + skillLevel +
                ", stadiumName=" + stadiumName +
                ", city=" + city +
                ", region=" + region +
                ", ageGroup=" + ageGroup +
                ", gender=" + gender +
                '}';
    }
}
