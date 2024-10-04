package com.yfmf.footlog.domain.match.dto;


import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.match.entity.Pro;
import com.yfmf.footlog.domain.match.enums.*;
import com.yfmf.footlog.domain.match.entity.Match;
import com.yfmf.footlog.domain.match.entity.MatchSchedule;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseDTO {

    private Long matchId;
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

    public MatchResponseDTO(Match match) {
        this(
                match.getMatchId(),
                match.getMatchEnrollUserId(),
                match.getMatchApplyUserId(),
                match.getMyClub(),
                match.getEnemyClub(),
                match.getMatchPhoto(),
                match.getMatchIntroduce(),
                match.getMatchSchedule(),
                match.getMatchPlayerQuantity(),
                match.getQuarterQuantity(),
                match.getFieldLocation(),
                match.getMatchCost(),
                match.getPro(),
                match.getClubLevel(),
                match.getMatchGender(),
                match.getMatchStatus()
        );
    }
}
