package com.yfmf.footlog.match.entity;


import com.yfmf.footlog.domain.club.Club;
import com.yfmf.footlog.match.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Match {

    // 경기 아이디
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long matchId;

    // 경기 게시물 생성 시간
    @Column(nullable = false)
    private LocalDateTime matchEnrollTime;

    // 경기 생성 유저 아이디
    @Column(nullable = false)
    private Long matchEnrollUserId;

    // 경기 신청 유저 아이디
    @Column(nullable = false)
    private Long matchApplyUserId;

    // 내 구단 - 구단 라인업, 매치 생성 매니저, 구단 이름, 구단 로고, 구단
    @OneToOne
    @JoinColumn(nullable = false)
    private Club myClub;

    // 상대 구단 - 구단 이름, 구단 로고
    @OneToOne
    @JoinColumn(nullable = false)
    private Club enemyClub;

    // 매치 대표사진/로고
    @Column
    private String matchPhoto;

    // 매치 설명
    @Column(length = 500)
    private String matchIntroduce;

    // 매치 일정
    @Embedded
    private MatchSchedule matchSchedule;

    // 매치 인원
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchPlayerQuantity matchPlayerQuantity;

    // 쿼터 수
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuarterQuantity quarterQuantity;

    // 구장 위치
    @Column(nullable = false)
    private String fieldLocation;

    // 매치 비용
    @Column
    private Integer matchCost;

    // 선출 여부 & 선출 수
    @Embedded
    private Pro pro;

    // 실력수치
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClubLevel clubLevel;

    // 성별 - male, female, mix
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchGender matchGender;

    // 경기 상태 - 매칭 전 'N', 진행 중 'Y', 종료 'E'
    @Column(nullable = false, columnDefinition = "char(1) default 'Y'")
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    public Match(LocalDateTime matchEnrollTime, Long matchEnrollUserId, Long matchApplyUserId, Club myClub, Club enemyClub, String matchPhoto, String matchIntroduce,
                 MatchSchedule matchSchedule, MatchPlayerQuantity matchPlayerQuantity, QuarterQuantity quarterQuantity, String fieldLocation, Integer matchCost, Pro pro,
                 ClubLevel clubLevel, MatchGender matchGender, MatchStatus matchStatus) {
        this.matchEnrollTime = matchEnrollTime;
        this.matchEnrollUserId = matchEnrollUserId;
        this.matchApplyUserId = matchApplyUserId;
        this.myClub = myClub;
        this.enemyClub = enemyClub;
        this.matchPhoto = matchPhoto;
        this.matchIntroduce = matchIntroduce;
        this.matchSchedule = matchSchedule;
        this.matchPlayerQuantity = matchPlayerQuantity;
        this.quarterQuantity = quarterQuantity;
        this.fieldLocation = fieldLocation;
        this.matchCost = matchCost;
        this.pro = pro;
        this.clubLevel = clubLevel;
        this.matchGender = matchGender;
        this.matchStatus = matchStatus;
    }

    public void setMatchIntroduce(String matchIntroduce) {
        this.matchIntroduce = matchIntroduce;
    }

}