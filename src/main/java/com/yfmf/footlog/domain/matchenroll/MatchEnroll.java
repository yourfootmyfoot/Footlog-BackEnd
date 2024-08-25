package com.yfmf.footlog.domain.matchenroll;

import com.yfmf.footlog.domain.matchenroll.enums.ClubLevel;
import com.yfmf.footlog.domain.matchenroll.enums.MatchGender;
import com.yfmf.footlog.domain.matchenroll.enums.PlayerCount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name= "MATCH_ENROLL")
public class MatchEnroll {

    @Id
    @GeneratedValue
    private Long matchEnrollId;

    @Column(nullable = false)
    private String matchTitle;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime matchDay;

    @Column(nullable = false)
    private Boolean isPro = false;

    @Column(nullable = false)
    private Integer matchCost = 0;

    @Column(nullable = false)
    private Integer playTime = 2;
    private Integer quarter;

    @Column(nullable = false)
    private String fieldLocation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayerCount playerCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchGender matchGender;

    // user
    @Column(nullable = false)
    private Long userId;
    private String userName;

    //club
    private String clubName;
    private String clubImageUrl;

    @Enumerated(EnumType.STRING)
    private ClubLevel clubLevel;

    public void updateMatch(String matchTitle, String description, LocalDateTime matchDay, Boolean isPro,
                            Integer matchCost, Integer playTime, Integer quarter, String fieldLocation,
                            PlayerCount playerCount, MatchGender matchGender, String userName, String clubName,
                            String clubImageUrl, ClubLevel clubLevel) {
        if (matchTitle != null) {
            this.matchTitle = matchTitle;
        }
        if (description != null) {
            this.description = description;
        }
        if (matchDay != null) {
            this.matchDay = matchDay;
        }
        if (isPro != null) {
            this.isPro = isPro;
        }
        if (matchCost != null) {
            this.matchCost = matchCost;
        }
        if (playTime != null) {
            this.playTime = playTime;
        }
        if (quarter != null) {
            this.quarter = quarter;
        }
        if (fieldLocation != null) {
            this.fieldLocation = fieldLocation;
        }
        if (playerCount != null) {
            this.playerCount = playerCount;
        }
        if (matchGender != null) {
            this.matchGender = matchGender;
        }
        if (userName != null) {
            this.userName = userName;
        }
        if (clubName != null) {
            this.clubName = clubName;
        }
        if (clubImageUrl != null) {
            this.clubImageUrl = clubImageUrl;
        }
        if (clubLevel != null) {
            this.clubLevel = clubLevel;
        }

        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public MatchEnroll(String matchTitle, String description, LocalDateTime matchDay, Boolean isPro,
                       Integer matchCost, Integer playTime, Integer quarter, String fieldLocation,
                       PlayerCount playerCount, MatchGender matchGender, Long userId, String userName,
                       String clubName, String clubImageUrl, ClubLevel clubLevel) {
        this.matchTitle = matchTitle;
        this.description = description;
        this.matchDay = matchDay;
        this.isPro = isPro;
        this.matchCost = matchCost;
        this.playTime = playTime;
        this.quarter = quarter;
        this.fieldLocation = fieldLocation;
        this.playerCount = playerCount;
        this.matchGender = matchGender;
        this.userId = userId;
        this.userName = userName;
        this.clubName = clubName;
        this.clubImageUrl = clubImageUrl;
        this.clubLevel = clubLevel;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
