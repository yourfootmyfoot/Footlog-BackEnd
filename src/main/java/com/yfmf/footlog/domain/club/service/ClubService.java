package com.yfmf.footlog.domain.club.service;

import com.yfmf.footlog.domain.club.exception.ClubDuplicatedException;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
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

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    // 클럽 등록
    @Transactional
    public void registClub(ClubRegistRequestDTO clubInfo) {
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
                clubInfo.getPeakHours(),
                clubInfo.getPeakDays()
        );
        clubRepository.save(newClub);
    }

    // 모든 클럽 조회
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    // 특정 클럽 조회
    public List<Club> getClubsByUserId(Long userId) {
        List<Club> clubs = clubRepository.findByUserId(userId);
        if (clubs.isEmpty()) {
            throw new ClubNotFoundException("해당 구단주로 등록된 클럽이 존재하지 않습니다.", "[ClubService] getClubsByUserId");
        }
        return clubs;
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
        club.setErollDate(clubInfo.getErollDate());
        club.setPeakHours(clubInfo.getPeakHours());
        club.setPeakDays(clubInfo.getPeakDays());
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
}
