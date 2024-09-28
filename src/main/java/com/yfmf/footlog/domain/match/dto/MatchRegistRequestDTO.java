package com.yfmf.footlog.domain.match.dto;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.match.entity.Pro;
import com.yfmf.footlog.domain.match.enums.*;
import com.yfmf.footlog.domain.match.entity.Match;
import com.yfmf.footlog.domain.match.entity.MatchSchedule;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MatchRegistRequestDTO {

    private Long matchEnrollUserId;
    private Long matchApplyUserId;
    private Club myClub;
    private Club enemyClub;
    private String matchPhoto;
    private String matchIntroduce;
    private MatchSchedule matchSchedule;
    private MatchPlayerQuantity matchPlayerQuantity;
    private QuarterQuantity quarterQuantity;
    private String fieldLocation;
    private Integer matchCost;
    private Pro pro;
    private ClubLevel clubLevel;
    private MatchGender matchGender;
    private MatchStatus matchStatus;

    // Match 엔티티로 변환하는 메소드
    public Match toEntity() {
        return new Match(
                this.matchEnrollUserId,
                this.matchApplyUserId,
                this.myClub,
                this.enemyClub,
                this.matchPhoto,
                this.matchIntroduce,
                this.matchSchedule,
                this.matchPlayerQuantity,
                this.quarterQuantity,
                this.fieldLocation,
                this.matchCost,
                this.pro,
                this.clubLevel,
                this.matchGender,
                this.matchStatus
        );
    }
}
