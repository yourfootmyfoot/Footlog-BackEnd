package com.yfmf.footlog.domain.club.service;

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
        Club newClub = new Club(
                clubInfo.getUserId(),  // userId를 사용하여 클럽 등록
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
    public Optional<Club> getClubById(Long clubId) {
        return clubRepository.findById(clubId);
    }

    // 클럽 업데이트
    @Transactional
    public void updateClub(Long clubId, ClubRegistRequestDTO clubInfo) {
        Optional<Club> optionalClub = clubRepository.findById(clubId);
        if (optionalClub.isPresent()) {
            Club club = optionalClub.get();
            club.setUserId(clubInfo.getUserId());
            club.setClubName(clubInfo.getClubName());
            club.setClubIntroduction(clubInfo.getClubIntroduction());
            club.setClubCode(clubInfo.getClubCode());
            club.setErollDate(clubInfo.getErollDate());
            club.setPeakHours(clubInfo.getPeakHours());
            club.setPeakDays(clubInfo.getPeakDays());
            clubRepository.save(club);
        } else {
            throw new RuntimeException("Club not found with id " + clubId);
        }
    }

    // 클럽 삭제
    @Transactional
    public void deleteClub(Long clubId) {
        if (clubRepository.existsById(clubId)) {
            clubRepository.deleteById(clubId);
        } else {
            throw new RuntimeException("Club not found with id " + clubId);
        }
    }
}
