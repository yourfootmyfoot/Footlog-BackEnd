package com.yfmf.footlog.domain.club.repository;

import com.yfmf.footlog.domain.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}
