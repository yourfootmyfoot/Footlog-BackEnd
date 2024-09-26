package com.yfmf.footlog.domain.club.service;

import com.yfmf.footlog.domain.club.dto.ClubRegistResponseDTO;
import com.yfmf.footlog.domain.club.entity.ClubMember;
import com.yfmf.footlog.domain.club.exception.ClubDuplicatedException;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.repository.ClubMemberRepository;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import com.yfmf.footlog.domain.club.dto.ClubRegistRequestDTO;
import com.yfmf.footlog.domain.club.entity.Club;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository, ClubMemberRepository clubMemberRepository) {
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
    }

    // 클럽 등록
    @Transactional
    public ClubRegistResponseDTO registClub(ClubRegistRequestDTO clubInfo) {
        log.info("클럽 등록 시도: 클럽 이름={}, 클럽 코드={}", clubInfo.getClubName(), clubInfo.getClubCode());
        // 중복된 클럽 코드 확인
        if (clubRepository.existsByClubCode(clubInfo.getClubCode())) {
            log.error("클럽 코드 중복: 클럽 코드={}", clubInfo.getClubCode());
            throw new ClubDuplicatedException("이미 존재하는 클럽 코드입니다.", "[ClubService] registClub");
        }

        // 클럽 등록 로직
        Club newClub = new Club(
                clubInfo.getUserId(),
                clubInfo.getClubName(),
                clubInfo.getClubIntroduction(),
                clubInfo.getClubCode(),
                clubInfo.getErollDate(),
                1,
                clubInfo.getDays(),
                clubInfo.getTimes(),
                clubInfo.getSkillLevel(),
                clubInfo.getStadiumName(),
                clubInfo.getCity(),
                clubInfo.getRegion(),
                clubInfo.getAgeGroup(),  // 연령대 추가
                clubInfo.getGender()     // 성별 추가
        );
        log.debug("등록할 클럽 정보: {}", newClub);
        clubRepository.save(newClub);
        log.info("클럽 등록 완료: 클럽 ID={}", newClub.getClubId());

        // 클럽 등록 결과 반환
        return new ClubRegistResponseDTO(
                newClub.getUserId(),
                newClub.getClubName(),
                newClub.getClubIntroduction(),
                newClub.getClubCode(),
                newClub.getErollDate(),
                newClub.getMemberCount(),
                newClub.getDays(),
                newClub.getTimes(),
                newClub.getSkillLevel(),
                newClub.getStadiumName(),
                newClub.getCity(),
                newClub.getRegion(),
                newClub.getAgeGroup(),  // 연령대 반환
                newClub.getGender()     // 성별 반환
        );
    }


    // 모든 클럽 조회
    public List<Club> getAllClubs() {
        log.info("모든 클럽 조회 요청");
        List<Club> clubs = clubRepository.findAll();
        log.info("조회된 클럽 수: {}", clubs.size());
        return clubs;
    }

    // 구단주 아이디로 특정 클럽 조회
    public List<Club> getClubsByUserId(Long userId) {
        log.info("구단주 ID={}에 대한 클럽 조회 요청", userId);
        List<Club> clubs = clubRepository.findByUserId(userId);
        if (clubs.isEmpty()) {
            log.warn("구단주 ID={}에 대한 클럽이 존재하지 않음", userId);
            throw new ClubNotFoundException("해당 구단주로 등록된 클럽이 존재하지 않습니다.", "[ClubService] getClubsByUserId");
        }
        log.info("조회된 클럽 수: {}", clubs.size());
        return clubs;
    }

    // 클럽 아이디로 특정 클럽 조회
    public Club getClubByClubId(Long clubId) {
        log.info("클럽 ID={}에 대한 클럽 조회 요청", clubId);
        Club club = clubRepository.findByClubId(clubId);
        if (club == null) {
            log.error("클럽 ID={}에 대한 클럽이 존재하지 않음", clubId);
            throw new ClubNotFoundException("해당 구단주로 등록된 클럽이 존재하지 않습니다.", "[ClubService] getClubsByClubId");
        }
        log.info("클럽 조회 성공: 클럽 ID={}", clubId);
        return club;
    }


    // 클럽 업데이트
    @Transactional
    public void updateClub(Long clubId, ClubRegistRequestDTO clubInfo) {
        log.info("클럽 ID={}에 대한 업데이트 시도", clubId);
        Optional<Club> optionalClub = clubRepository.findById(clubId);
        if (optionalClub.isEmpty()) {
            log.error("업데이트할 클럽이 존재하지 않음: 클럽 ID={}", clubId);
            throw new ClubNotFoundException("업데이트할 클럽이 존재하지 않습니다.", "[ClubService] updateClub");
        }

        Club club = optionalClub.get();
        club.setUserId(clubInfo.getUserId());
        club.setClubName(clubInfo.getClubName());
        club.setClubIntroduction(clubInfo.getClubIntroduction());
        club.setClubCode(clubInfo.getClubCode());
        club.setTimes(clubInfo.getTimes());
        club.setDays(clubInfo.getDays());
        club.setSkillLevel(clubInfo.getSkillLevel());
        club.setStadiumName(clubInfo.getStadiumName());
        club.setCity(clubInfo.getCity());
        club.setRegion(clubInfo.getRegion());
        log.debug("업데이트된 클럽 정보: {}", club);
        clubRepository.save(club);
        log.info("클럽 업데이트 완료: 클럽 ID={}", clubId);
    }

    // 클럽 삭제
    @Transactional
    public void deleteClub(Long clubId) {
        log.info("클럽 삭제 시도: 클럽 ID={}", clubId);
        if (!clubRepository.existsById(clubId)) {
            log.error("삭제할 클럽이 존재하지 않음: 클럽 ID={}", clubId);
            throw new ClubNotFoundException("삭제할 클럽이 존재하지 않습니다.", "[ClubService] deleteClub");
        }
        clubRepository.deleteById(clubId);
        log.info("클럽 삭제 완료: 클럽 ID={}", clubId);
    }

}
