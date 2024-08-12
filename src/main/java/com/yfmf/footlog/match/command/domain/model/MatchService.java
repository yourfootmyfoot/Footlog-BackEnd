package com.yfmf.footlog.match.command.domain.model;

import org.springframework.stereotype.Service;

@Service
public class MatchService {
    public Object registNewMatch(MatchRegistRequestDTO newMatch) {

        Match match = new Match(
                newMatch.getMatchEnrollTime(),
                newMatch.getMatchEnrollUserId(),
                newMatch.getMatchApplyUserId(),
                newMatch.getMyClub(),
                newMatch.getEnemyClub(),
                newMatch.getMatchPhoto(),
                newMatch.getMatchIntroduce(),
                newMatch.getMatchSchedule(),
                newMatch.getMatchPlayerQuantity(),
                newMatch.getQuarterQuantity(),
                newMatch.getFieldLocation(),
                newMatch.getMatchCost(),
                newMatch.getIsPro(),
                newMatch.getProQuantity(),
                newMatch.getClubLevel(),
                newMatch.getMatchGender(),
                newMatch.getMatchStatus()
        );

        )
    }
}
