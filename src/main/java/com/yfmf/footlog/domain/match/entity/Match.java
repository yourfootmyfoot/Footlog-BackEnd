package com.yfmf.footlog.domain.match.entity;


import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.match.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_match")
public class Match extends BaseTimeEntity {

    // 경기 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 사용
    @Column(name = "match_id", nullable = false)
    private Long matchId;

    // 경기 생성 유저 아이디
    @Column(name="match_enroll_user_id", nullable = false)
    private Long matchEnrollUserId;

    // 경기 신청 유저 아이디
    @Column(name="match_apply_user_id", nullable = false)
    private Long matchApplyUserId;

    // 내 구단 - 구단 라인업, 매치 생성 매니저, 구단 이름, 구단 로고, 구단
    @ManyToOne
    @JoinColumn(name = "my_club_id", referencedColumnName = "CLUB_ID", nullable = false)
    private Club myClub;

    // 상대 구단 - 구단 이름, 구단 로고
    @ManyToOne
    @JoinColumn(name = "enemy_club_id", referencedColumnName = "CLUB_ID", nullable = true)
    private Club enemyClub;

    // 매치 대표사진
    @Column(name="match_photo")
    private String matchPhoto;

    // 매치 설명
    @Column(name="match_introduce",length = 500)
    private String matchIntroduce;

    // 매치 일정
    @Embedded
    private MatchSchedule matchSchedule;

    // 매치 인원
    @Column(name="match_player_quantity",nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchPlayerQuantity matchPlayerQuantity;

    // 쿼터 수
    @Enumerated(EnumType.STRING)
    @Column(name="quarter_quantity",nullable = false)
    private QuarterQuantity quarterQuantity;

    // 구장 위치
    @Column(name="field_location",nullable = false)
    private String fieldLocation;

    // 매치 비용
    @Column(name="match_cost")
    private Integer matchCost;

    // 선출 여부 & 선출 수
    @Embedded
    private Pro pro;

    // 실력수치
    @Column(name="club_level",nullable = false)
    @Enumerated(EnumType.STRING)
    private ClubLevel clubLevel;

    // 성별 - male, female, mix
    @Column(name="match_gender",nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchGender matchGender;

    // 경기 상태
    @Column(name="match_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    @Builder
    public Match(Long matchEnrollUserId, Long matchApplyUserId, Club myClub, Club enemyClub, String matchPhoto, String matchIntroduce,
                 MatchSchedule matchSchedule, MatchPlayerQuantity matchPlayerQuantity, QuarterQuantity quarterQuantity,
                 String fieldLocation, Integer matchCost, Pro pro, ClubLevel clubLevel, MatchGender matchGender, MatchStatus matchStatus) {
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

    public void updateMatch(String matchIntroduce, MatchSchedule matchSchedule, MatchPlayerQuantity matchPlayerQuantity,
                            QuarterQuantity quarterQuantity, String fieldLocation, Integer matchCost, Pro pro,
                            ClubLevel clubLevel, MatchGender matchGender, MatchStatus matchStatus) {

        if (matchIntroduce != null) {
            this.matchIntroduce = matchIntroduce;
        }
        if (matchSchedule != null) {
            this.matchSchedule = matchSchedule;
        }
        if (matchPlayerQuantity != null) {
            this.matchPlayerQuantity = matchPlayerQuantity;
        }
        if (quarterQuantity != null) {
            this.quarterQuantity = quarterQuantity;
        }
        if (fieldLocation != null) {
            this.fieldLocation = fieldLocation;
        }
        if (matchCost != null) {
            this.matchCost = matchCost;
        }
        if (pro != null) {
            this.pro = pro;
        }
        if (clubLevel != null) {
            this.clubLevel = clubLevel;
        }
        if (matchGender != null) {
            this.matchGender = matchGender;
        }
        if (matchStatus != null) {
            this.matchStatus = matchStatus;
        }
    }

}