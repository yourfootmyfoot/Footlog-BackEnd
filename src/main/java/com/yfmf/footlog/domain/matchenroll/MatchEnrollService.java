package com.yfmf.footlog.domain.matchenroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MatchEnrollService {

    private MatchEnrollRepository matchEnrollRepository;

    @Autowired
    public MatchEnrollService(MatchEnrollRepository matchEnrollRepository) {
        this.matchEnrollRepository = matchEnrollRepository;
    }

    @Transactional
    public MatchEnroll registerMatchEnroll(MatchEnroll matchEnroll) {
        return matchEnrollRepository.save(matchEnroll);
    }

    public Optional<MatchEnroll> findMatchEnrollByMatchId(Long matchEnrollId) {
        return matchEnrollRepository.findById(matchEnrollId);
    }

    @Transactional
    public MatchEnroll updateMatch(Long matchEnrollId, MatchEnroll updatedMatchEnroll) {
        MatchEnroll existingMatch = matchEnrollRepository.findById(matchEnrollId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 경기를 찾을 수 없습니다. id=" + matchEnrollId));

        existingMatch.updateMatch(
                updatedMatchEnroll.getMatchTitle(),
                updatedMatchEnroll.getDescription(),
                updatedMatchEnroll.getMatchDay(),
                updatedMatchEnroll.getIsPro(),
                updatedMatchEnroll.getMatchCost(),
                updatedMatchEnroll.getPlayTime(),
                updatedMatchEnroll.getQuarter(),
                updatedMatchEnroll.getFieldLocation(),
                updatedMatchEnroll.getPlayerCount(),
                updatedMatchEnroll.getMatchGender(),
                updatedMatchEnroll.getUserName(),
                updatedMatchEnroll.getClubName(),
                updatedMatchEnroll.getClubImageUrl(),
                updatedMatchEnroll.getClubLevel());

        return matchEnrollRepository.save(existingMatch);

    }

    @Transactional
    public void deleteMatch(Long matchEnrollId) {
        MatchEnroll matchEnroll = matchEnrollRepository.findById(matchEnrollId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 경기를 찾을 수 없습니다. id=" + matchEnrollId));
        matchEnrollRepository.delete(matchEnroll);
    }

}
