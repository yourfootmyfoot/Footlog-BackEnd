package com.yfmf.footlog.domain.match.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.match.entity.Pro;
import com.yfmf.footlog.domain.match.enums.*;
import com.yfmf.footlog.domain.match.entity.Match;
import com.yfmf.footlog.domain.match.entity.MatchSchedule;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Data
@NoArgsConstructor
public class MatchRegisterRequestDTO {

    private Long matchEnrollUserId;
    private Long matchApplyUserId;
    private Long myClubId;
    private Long enemyClubId;
    private String matchPhoto;
    private String matchIntroduce;

    // Replace MatchSchedule with individual date and time fields
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate matchDate;

    private String matchStartTime;
    private String matchEndTime;

    private MatchPlayerQuantity matchPlayerQuantity;
    private QuarterQuantity quarterQuantity;
    private String fieldLocation;
    private Integer matchCost;
    private Pro pro;
    private ClubLevel clubLevel;
    private MatchGender matchGender;
    private MatchStatus matchStatus;

    public Match toEntity(Club myClub, Club enemyClub) {

        LocalTime startTime = null;
        LocalTime endTime = null;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            if (matchStartTime != null) {
                startTime = LocalTime.parse(matchStartTime, timeFormatter);
            }
            if (matchEndTime != null) {
                endTime = LocalTime.parse(matchEndTime, timeFormatter);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format provided for matchStartTime or matchEndTime. Expected format is HH:mm.", e);
        }

        MatchSchedule matchSchedule = null;
        if (matchDate != null && startTime != null && endTime != null) {
            matchSchedule = new MatchSchedule(matchDate, startTime, endTime);
        }

        return new Match(
                this.matchEnrollUserId,
                this.matchApplyUserId,
                myClub,
                enemyClub,
                this.matchPhoto,
                this.matchIntroduce,
                matchSchedule,
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
