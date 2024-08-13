package com.yfmf.footlog.match.command.domain.model;

import com.yfmf.footlog.match.command.domain.model.enums.*;
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
    private Boolean isPro;
    private Integer proQuantity;
    private ClubLevel clubLevel;
    private MatchGender matchGender;
    private MatchStatus matchStatus;
}
