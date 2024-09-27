package com.yfmf.footlog.domain.match.service;

import com.yfmf.footlog.domain.match.dto.LoadMatchResponseDTO;
import com.yfmf.footlog.domain.match.dto.MatchRegistRequestDTO;
import com.yfmf.footlog.domain.match.entity.Match;
import com.yfmf.footlog.domain.match.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // 경기 조회
    public List<LoadMatchResponseDTO> loadAllMatches() {
        return matchRepository.findAll().stream()
                .map(LoadMatchResponseDTO::new)
                .toList();
    }

    // 경기 등록
    public void registMatch(MatchRegistRequestDTO matchInfo) {
        matchRepository.save(matchInfo.toEntity());
    }

    // 경기 수정
    public void modifyMatch(Long matchId, String matchIntroduce) {
        Match foundMatch = matchRepository.findById(matchId)
                .orElseThrow(IllegalArgumentException::new);
        foundMatch.setMatchIntroduce(matchIntroduce);
        matchRepository.save(foundMatch);  // 변경된 엔티티를 저장
    }

    // 경기 삭제
    public void removeMatch(Long matchId) {
        matchRepository.deleteById(matchId);
    }

    // matchId로 경기 찾기
    public LoadMatchResponseDTO findMatchByMatchId(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(IllegalArgumentException::new);
        return new LoadMatchResponseDTO(match);
    }
}
