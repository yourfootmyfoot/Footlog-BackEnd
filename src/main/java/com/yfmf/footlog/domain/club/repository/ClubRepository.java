package com.yfmf.footlog.domain.club.repository;

import com.yfmf.footlog.domain.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByUserId(Long userId);

    boolean existsByClubCode(String clubCode);

    Club findByClubId(Long clubId);

    boolean existsByClubName(String clubName);
}
