package com.yfmf.footlog.domain.matchenroll;

import com.yfmf.footlog.domain.matchenroll.enums.ClubLevel;
import com.yfmf.footlog.domain.matchenroll.enums.MatchGender;
import com.yfmf.footlog.domain.matchenroll.enums.PlayerCount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
