package com.yfmf.footlog.domain.match.service;

import com.yfmf.footlog.domain.match.dto.MatchRegisterRequestDTO;
import com.yfmf.footlog.domain.match.dto.MatchResponseDTO;
import com.yfmf.footlog.domain.match.entity.Match;
import com.yfmf.footlog.domain.match.entity.MatchSchedule;
import com.yfmf.footlog.domain.match.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // 모든 경기 조회
    public List<MatchResponseDTO> findAllMatches() {
        return matchRepository.findAll().stream()
                .map(MatchResponseDTO::new)
                .toList();
    }

    // 단일 경기 조회
    public MatchResponseDTO findMatchByMatchId(Long matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(()->new IllegalArgumentException("등록된 경기를 찾을 수 없습니다. id=" + matchId));
        return new MatchResponseDTO(match);
    }

    // 경기 등록
    @Transactional
    public MatchResponseDTO saveMatch(MatchRegisterRequestDTO matchInfo) {
        Match savedMatch = matchRepository.save(matchInfo.toEntity());
        return new MatchResponseDTO(savedMatch);
    }

    // 경기 수정
    @Transactional
    public MatchResponseDTO updateMatch(Long matchId, MatchRegisterRequestDTO updateRequestDTO) {
        Match foundMatch = getMatchOrThrow(matchId);

        LocalDate matchDate = updateRequestDTO.getMatchDate();

        // matchStartTime, and matchEndTime from Strings if provided
        LocalTime matchStartTime = null;
        LocalTime matchEndTime = null;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            if (updateRequestDTO.getMatchStartTime() != null) {
                matchStartTime = LocalTime.parse(updateRequestDTO.getMatchStartTime(), timeFormatter);
            }
            if (updateRequestDTO.getMatchEndTime() != null) {
                matchEndTime = LocalTime.parse(updateRequestDTO.getMatchEndTime(), timeFormatter);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date or time format provided.", e);
        }

        // Create a MatchSchedule object if all necessary fields are available
        MatchSchedule matchSchedule = null;
        if (matchDate != null && matchStartTime != null && matchEndTime != null) {
            matchSchedule = new MatchSchedule(matchDate, matchStartTime, matchEndTime);
        }

        foundMatch.updateMatch(
                updateRequestDTO.getMatchIntroduce(),
                matchSchedule,
                updateRequestDTO.getMatchPlayerQuantity(),
                updateRequestDTO.getQuarterQuantity(),
                updateRequestDTO.getFieldLocation(),
                updateRequestDTO.getMatchCost(),
                updateRequestDTO.getPro(),
                updateRequestDTO.getClubLevel(),
                updateRequestDTO.getMatchGender(),
                updateRequestDTO.getMatchStatus()
        );

        matchRepository.save(foundMatch);
        return new MatchResponseDTO(foundMatch);
    }

    // 경기 삭제
    @Transactional
    public void removeMatch(Long matchId) {
        Match foundMatch = getMatchOrThrow(matchId);
        matchRepository.delete(foundMatch);
    }

    private Match getMatchOrThrow(Long matchId) {
        return matchRepository.findById(matchId).orElseThrow(()-> new IllegalArgumentException("해당 ID의 경기를 찾을 수 없습니다. id=" + matchId));
    }

}
