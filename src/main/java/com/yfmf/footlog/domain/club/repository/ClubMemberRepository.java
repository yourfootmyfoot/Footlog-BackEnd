package com.yfmf.footlog.domain.club.repository;

import com.yfmf.footlog.domain.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    // 클럽 ID로 구단원 조회
    List<ClubMember> findByClubId(Long clubId);

    // 회원이 클럽에 이미 가입되어 있는지 확인
    boolean existsByMemberIdAndClubId(Long memberId, Long clubId);

    // 구단원 탈퇴
    void deleteByMemberIdAndClubId(Long memberId, Long clubId);
}
