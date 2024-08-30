package com.yfmf.footlog.match.model.dto;

import com.yfmf.footlog.match.model.entity.Match;
import com.yfmf.footlog.match.model.entity.MatchSchedule;
import com.yfmf.footlog.match.model.entity.Pro;
import com.yfmf.footlog.match.command.domain.model.enums.*;
import com.yfmf.footlog.match.model.enums.*;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MatchRegistRequestDTO {

    private LocalDateTime matchEnrollTime;
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

    // Id 없이 추가
    public MatchRegistRequestDTO(Match match) {
        this(
                match.getMatchEnrollTime(),
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
