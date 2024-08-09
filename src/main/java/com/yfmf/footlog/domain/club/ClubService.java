package com.yfmf.footlog.domain.club;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Transactional
    public void registClub(ClubRegistRequestDTO clubInfo) {

        Club newClub = new Club(
                clubInfo.getClubOwner(),
                clubInfo.getClubName(),
                clubInfo.getClubIntroduction(),
                clubInfo.getClubCode(),
                clubInfo.getErollDate(),
                clubInfo.getPeakHours(),
                clubInfo.getPeakDays()
        );

        clubRepository.save(newClub);
    }
}
