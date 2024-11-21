package com.yfmf.footlog.domain.guest.entity;

import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.guest.enums.RecruitmentStatus;
import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_guest_recruitment")
public class GuestRecruitment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long clubId;  // 구단 ID


    @Column(nullable = false)
    private LocalDate matchDate;  // 경기 날짜

    @Column(nullable = false)
    private LocalTime matchStartTime;  // 경기 시작 시간

    @Column(nullable = false)
    private LocalTime matchEndTime;  // 경기 종료 시간


    @Column(nullable = false)
    private String location;  // 경기 장소

    @Column(nullable = false)
    private Integer requiredNumber;  // 필요 인원

    @ElementCollection
    @CollectionTable(name = "tbl_guest_recruitment_positions")
    @Enumerated(EnumType.STRING)
    private List<Position> requiredPositions;  // 필요 포지션들

    @Column(nullable = false)
    private Integer pay;  // 용병비

    @Column(length = 500)
    private String description;  // 추가 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus status = RecruitmentStatus.RECRUITING;  // 모집 상태 (RECRUITING, COMPLETED, EXPIRED)

    @Builder
    public GuestRecruitment(String title, Long userId,Long clubId, LocalDate matchDate, LocalTime matchStartTime,
                            LocalTime matchEndTime, String location,
                            Integer requiredNumber, List<Position> requiredPositions,
                            Integer pay, String description) {
        this.title = title;
        this.userId = userId;
        this.clubId = clubId;
        this.matchDate = matchDate;
        this.matchStartTime = matchStartTime;
        this.matchEndTime = matchEndTime;
        this.location = location;
        this.requiredNumber = requiredNumber;
        this.requiredPositions = requiredPositions;
        this.pay = pay;
        this.description = description;
    }

    public Long getMatchEnrollUserId() {
        return this.userId;
    }

    public void updateRecruitment(
            String title,
            LocalDate matchDate,
            LocalTime matchStartTime,
            LocalTime matchEndTime,
            String location,
            int requiredNumber,
            List<Position> requiredPositions,
            int pay,
            String description) {

        this.title = title;
        this.matchDate = matchDate;
        this.matchStartTime = matchStartTime;
        this.matchEndTime = matchEndTime;
        this.location = location;
        this.requiredNumber = requiredNumber;
        this.requiredPositions = requiredPositions;
        this.pay = pay;
        this.description = description;
    }
}