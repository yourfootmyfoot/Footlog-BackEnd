package com.yfmf.footlog.match.command.domain.model;

import com.yfmf.footlog.match.command.domain.model.dao.MatchMapper;
import com.yfmf.footlog.match.command.domain.model.dto.LoadMatchResponseDTO;
import com.yfmf.footlog.match.command.domain.model.dto.MatchRegistRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private MatchRepository matchRepository;
    private MatchMapper matchMapper;

    public MatchService(MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    // 의존성 주입
    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // 경기 조회
    public List<LoadMatchResponseDTO> loadAllMatches() {

        return matchRepository.findAll()
                .stream()
                .map(LoadMatchResponseDTO::new)
                .toList();
    }


    // 경기 등록
    public void registMatch(MatchRegistRequestDTO matchInfo) {

        Match newMatch = new Match(
                matchInfo.getMatchEnrollTime(),
                matchInfo.getMatchEnrollUserId(),
                matchInfo.getMatchApplyUserId(),
                matchInfo.getMyClub(),
                matchInfo.getEnemyClub(),
                matchInfo.getMatchPhoto(),
                matchInfo.getMatchIntroduce(),
                matchInfo.getMatchSchedule(),
                matchInfo.getMatchPlayerQuantity(),
                matchInfo.getQuarterQuantity(),
                matchInfo.getFieldLocation(),
                matchInfo.getMatchCost(),
                matchInfo.getPro(),
                matchInfo.getClubLevel(),
                matchInfo.getMatchGender(),
                matchInfo.getMatchStatus()
        );

        matchRepository.save(newMatch);
    }

    // 경기 수정
    public void modifyMatch(Long matchId, String MatchIntroduce) {
        Match foundMatch = matchRepository.findById(matchId).orElseThrow(IllegalAccessError::new);
        foundMatch.setMatchIntroduce(MatchIntroduce);

    }

    // 경기 삭제
    public void removeMatch(Long matchId) {

        matchRepository.deleteById(matchId);


    }

    public List<LoadMatchResponseDTO> findAllMatches() {

        return matchMapper.findAllMatches();
    }
}
