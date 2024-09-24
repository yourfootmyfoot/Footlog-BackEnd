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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
        // 중복된 클럽 코드 확인
        if (clubRepository.existsByClubCode(clubInfo.getClubCode())) {
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
        System.out.println(newClub);
        clubRepository.save(newClub);

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
        return clubRepository.findAll();
    }

    // 구단주 아이디로 특정 클럽 조회
    public List<Club> getClubsByUserId(Long userId) {
        List<Club> clubs = clubRepository.findByUserId(userId);
        if (clubs.isEmpty()) {
            throw new ClubNotFoundException("해당 구단주로 등록된 클럽이 존재하지 않습니다.", "[ClubService] getClubsByUserId");
        }
        return clubs;
    }

    // 클럽 아이디로 특정 클럽 조회
    public Club getClubByClubId(Long clubId) {
        Club club = clubRepository.findByClubId(clubId);
        if (club == null) {
            throw new ClubNotFoundException("해당 구단주로 등록된 클럽이 존재하지 않습니다.", "[ClubService] getClubsByClubId");
        }
        return club;
    }


    // 클럽 업데이트
    @Transactional
    public void updateClub(Long clubId, ClubRegistRequestDTO clubInfo) {
        Optional<Club> optionalClub = clubRepository.findById(clubId);
        if (optionalClub.isEmpty()) {
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
        clubRepository.save(club);
    }

    // 클럽 삭제
    @Transactional
    public void deleteClub(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new ClubNotFoundException("삭제할 클럽이 존재하지 않습니다.", "[ClubService] deleteClub");
        }
        clubRepository.deleteById(clubId);
    }

    /**
     * 구단원 가입
     */
    @Transactional
    public void joinClub(Long userId, Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubService] joinClub"));

        // 이미 가입된 구단원인지 확인
        if (clubMemberRepository.existsByUserIdAndClub_ClubId(userId, clubId)) {
            throw new IllegalArgumentException("이미 구단에 가입된 회원입니다.");
        }

        // 구단원 추가
        ClubMember clubMember = new ClubMember(userId, club);
        clubMemberRepository.save(clubMember);

        // 구단원의 수를 증가시킴
        club.setMemberCount(club.getMemberCount() + 1);
        clubRepository.save(club);
    }

    /**
     * 구단원 탈퇴
     */
    @Transactional
    public void leaveClub(Long userId, Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubService] leaveClub"));

        // 구단원 탈퇴
        if (!clubMemberRepository.existsByUserIdAndClub_ClubId(userId, clubId)) {
            throw new IllegalArgumentException("구단에 가입되어 있지 않은 회원입니다.");
        }

        clubMemberRepository.deleteByUserIdAndClub_ClubId(userId, clubId);

        // 구단원의 수를 감소시킴
        club.setMemberCount(club.getMemberCount() - 1);
        clubRepository.save(club);
    }
}
