package com.yfmf.footlog.domain.club.repository;

import com.yfmf.footlog.domain.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    boolean existsByUserIdAndClub_ClubId(Long userId, Long clubId);
    void deleteByUserIdAndClub_ClubId(Long userId, Long clubId);
}
