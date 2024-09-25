package com.yfmf.footlog.domain.club.repository;

import com.yfmf.footlog.domain.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    boolean existsByMemberIdAndClubId(Long memberId, Long clubId);

    void deleteByMemberIdAndClubId(Long memberId, Long clubId);
}
