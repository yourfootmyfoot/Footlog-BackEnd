package com.yfmf.footlog.match.command.domain.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity(name = "tbl_match")
@Table
public class Match {

    @Id // 기본키 == PK
    @Column(name = "MATCH_ID", nullable = false) // DB 테이블의 컬럼명
    // IDENTITY 전략 : 데이터베이스가 각 행을 삽입할 때 기본 키 값을 자동으로 생성하고, 이 값을 JPA 엔티티에 할당한다.
    // 주로 MySQL, PostgreSQL, SQL Server 등 여러 데이터베이스 시스템에서 지원된다.
    // 데이터베이스의 'AUTO_INCREMENT'기능과 유사하게 동작한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 와 같은 역할 기본키를 자동으로 증가시켜준다.
    private long matchId;

    // 경기 게시물 생성 시간
    @Column(name = "MATCH_ENROLL_TIME", nullable = false)
    private LocalDateTime matchEnrollTime;

    // 경기 게시물 생성 유저 아이디
    @Column(name = "MATCH_ENROLL_USER_ID", nullable = false)
    private long matchEnrollUserId;

    // 경기 게시물 생성 유저 아이디
    @Column(name = "MATCH_APPLY_USER_ID", nullable = false)
    private long matchApplyUserId;

    // 내 구단 - 구단 라인업, 매치 생성 매니저, 구단 이름, 구단 로고, 구단
    @Embedded
    private Club myClub;

    // 상대 구단 - 구단 라인업, 매치 생성 매니저, 구단 이름, 구단 로고, 구단
    @Embedded
    private Club enemyClub;

    // 매치 대표사진/로고
    @Column(name = "MATCH_PHOTO")
    private String matchPhoto;

    // 매치 설명
    @Column(name = "MATCH_Introduce", length = 500)
    private String matchIntroduce;

    // 경기날짜
    @Column(name = "MATCH_DATE", nullable = false)
    private LocalDate matchDate;

    // 경기 시작 시간
    @Column(name = "MATCH_START_TIME", nullable = false)
    private LocalTime matchStartTime;

    // 경기 종료 시간 - 경기 시작 시간을 설정하면 +2시간을 해주고 뒤에 수정 가능
    @Column(name = "MATCH_END_TIME", nullable = false)
    private LocalTime matchEndTime;

    // 경기 시간
    @Column(name = "MATCH_TIME", nullable = false)
    private Duration matchTime;

    // 매치 인원 - 11명? 22명?
    @Column(name = "MATCH_PLAYER_QUANTITY", nullable = false)
    private int matchPlayerQuantity;

    // 쿼터 수 - 기본 4쿼터, 경기시간 설정 시 30분으로 나눠서 쿼터 수 자동으로 선택해줌, 수정 가능
    @Column(name = "QUARTER_QUANTITY", nullable = false)
    private int quarterQuantity;

    // 구장 위치
    @Column(name = "FIELD_LOCATION", nullable = false)
    private String fieldLocation;

    // 매치 비용
    @Column(name = "MATCH_COST")
    private int matchCost;

    // 팀원 중 선출 여부
    @Column(name = "IS_PRO", nullable = false)
    private String isPro;

    // 실력수치
    @Column(name = "CLUB_LEVEL", nullable = false)
    private String clubLevel;

    // 성별
    @Column(name = "MATCH_GENDER", nullable = false)
    private String matchGender;

    // 경기 상태 - 매칭 전 'N', 진행 중 'Y', 종료 'E'
    @Column(name = "MATCH_STATUS", nullable = false, columnDefinition = "char(1) default 'Y'")
    private String matchStatus;

    public Match() {
    }


    public Match(long matchId, LocalDateTime matchEnrollTime, long matchEnrollUserId, long matchApplyUserId,
                 Club myClub, Club enemyClub, String matchPhoto, String matchIntroduce, LocalDate matchDate,
                 LocalTime matchStartTime, LocalTime matchEndTime, Duration matchTime, int matchPlayerQuantity,
                 int quarterQuantity, String fieldLocation, int matchCost, String isPro, String clubLevel,
                 String matchGender, String matchStatus) {

        this.matchId = matchId;
        this.matchEnrollTime = matchEnrollTime;
        this.matchEnrollUserId = matchEnrollUserId;
        this.matchApplyUserId = matchApplyUserId;
        this.myClub = myClub;
        this.enemyClub = enemyClub;
        this.matchPhoto = matchPhoto;
        this.matchIntroduce = matchIntroduce;
        this.matchDate = matchDate;
        this.matchStartTime = matchStartTime;
        this.matchEndTime = matchEndTime;
        this.matchTime = matchTime;
        this.matchPlayerQuantity = matchPlayerQuantity;
        this.quarterQuantity = quarterQuantity;
        this.fieldLocation = fieldLocation;
        this.matchCost = matchCost;
        this.isPro = isPro;
        this.clubLevel = clubLevel;
        this.matchGender = matchGender;
        this.matchStatus = matchStatus;
    }

    public long getMatchId() {
        return matchId;
    }

    public LocalDateTime getMatchEnrollTime() {
        return matchEnrollTime;
    }

    public long getMatchEnrollUserId() {
        return matchEnrollUserId;
    }

    public long getMatchApplyUserId() {
        return matchApplyUserId;
    }

    public Club getMyClub() {
        return myClub;
    }

    public Club getEnemyClub() {
        return enemyClub;
    }

    public String getMatchPhoto() {
        return matchPhoto;
    }

    public String getMatchIntroduce() {
        return matchIntroduce;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public LocalTime getMatchStartTime() {
        return matchStartTime;
    }

    public LocalTime getMatchEndTime() {
        return matchEndTime;
    }

    public Duration getMatchTime() {
        return matchTime;
    }

    public int getMatchPlayerQuantity() {
        return matchPlayerQuantity;
    }

    public int getQuarterQuantity() {
        return quarterQuantity;
    }

    public String getFieldLocation() {
        return fieldLocation;
    }

    public int getMatchCost() {
        return matchCost;
    }

    public String getIsPro() {
        return isPro;
    }

    public String getClubLevel() {
        return clubLevel;
    }

    public String getMatchGender() {
        return matchGender;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", matchEnrollTime=" + matchEnrollTime +
                ", matchEnrollUserId=" + matchEnrollUserId +
                ", matchApplyUserId=" + matchApplyUserId +
                ", myClub=" + myClub +
                ", enemyClub=" + enemyClub +
                ", matchPhoto='" + matchPhoto + '\'' +
                ", matchIntroduce='" + matchIntroduce + '\'' +
                ", matchDate=" + matchDate +
                ", matchStartTime=" + matchStartTime +
                ", matchEndTime=" + matchEndTime +
                ", matchTime=" + matchTime +
                ", matchPlayerQuantity=" + matchPlayerQuantity +
                ", quarterQuantity=" + quarterQuantity +
                ", fieldLocation='" + fieldLocation + '\'' +
                ", matchCost=" + matchCost +
                ", isPro='" + isPro + '\'' +
                ", clubLevel='" + clubLevel + '\'' +
                ", matchGender='" + matchGender + '\'' +
                ", matchStatus='" + matchStatus + '\'' +
                '}';
    }
}