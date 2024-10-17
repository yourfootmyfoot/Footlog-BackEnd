package com.yfmf.footlog.domain.match.service;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import com.yfmf.footlog.domain.match.dto.MatchRegisterRequestDTO;
import com.yfmf.footlog.domain.match.dto.MatchResponseDTO;
import com.yfmf.footlog.domain.match.entity.Match;
import com.yfmf.footlog.domain.match.entity.MatchSchedule;
import com.yfmf.footlog.domain.match.enums.MatchStatus;
import com.yfmf.footlog.domain.match.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final ClubRepository clubRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, ClubRepository clubRepository) {
        this.matchRepository = matchRepository;
        this.clubRepository = clubRepository;
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
        Club myClub = clubRepository.findById(matchInfo.getMyClubId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid myClub ID: " + matchInfo.getMyClubId()));

        // 초기 등록시 enemyClub null
        Match savedMatch = matchRepository.save(matchInfo.toEntity(myClub, null));

        log.warn("[MatchService] savedMatch + ", savedMatch);
        MatchResponseDTO matchResponse = new MatchResponseDTO(savedMatch);
        log.info("[MatchService] savedMatch" + matchResponse);
        return matchResponse;
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


    @Transactional
    public Match applyForMatch(Long matchId, Long applyingUserId, Long enemyClubId) {
        // 매치 아이디로 매치 찾기
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매치를 찾을 수 없습니다."));

        // 매치 상태가 'WAITING' 또는 'PENDING'인지 확인
        if (match.getMatchStatus() != MatchStatus.WAITING && match.getMatchStatus() != MatchStatus.PENDING) {
            throw new IllegalStateException("현재 매칭 신청이 불가능한 상태입니다.");
        }

        // 상대 구단 찾기
        Club enemyClub = clubRepository.findById(enemyClubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 구단을 찾을 수 없습니다."));

        // 매치 상태 및 상대 유저, 구단 정보 업데이트
        match.updateApplyInfo(applyingUserId, enemyClub, MatchStatus.PENDING);

        // 변경 사항 저장
        return matchRepository.save(match);
    }

    @Transactional
    public Match acceptMatch(Long matchId, Long matchOwnerId) {
        // 매치 아이디로 매치 찾기
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매치를 찾을 수 없습니다."));

        // 매치 상태가 'PENDING'인지 확인
        if (match.getMatchStatus() != MatchStatus.PENDING) {
            throw new IllegalStateException("현재 매칭 수락이 불가능한 상태입니다.");
        }

        // 매치 소유자 확인
        if (!match.getMatchEnrollUserId().equals(matchOwnerId)) {
            throw new IllegalArgumentException("매칭 수락 권한이 없습니다.");
        }

        // 매치 상태를 ACCEPTED로 변경
        match.acceptMatchStatus();

        // 변경 사항 저장
        return matchRepository.save(match);
    }

    @Transactional
    public Match rejectMatch(Long matchId, Long matchOwnerId) {
        // 매치 아이디로 매치 찾기
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매치를 찾을 수 없습니다."));

        // 매치 상태가 'PENDING'인지 확인
        if (match.getMatchStatus() != MatchStatus.PENDING) {
            throw new IllegalStateException("현재 매칭 거절이 불가능한 상태입니다.");
        }

        // 매치 소유자 확인
        if (!match.getMatchEnrollUserId().equals(matchOwnerId)) {
            throw new IllegalArgumentException("매칭 거절 권한이 없습니다.");
        }

        // 매치 상태를 WAITING 변경
        match.rejectMatchStatus();

        // 변경 사항 저장
        return matchRepository.save(match);
    }
}
