package com.yfmf.footlog.domain.club.repository;


import com.yfmf.footlog.domain.club.entity.JoinRequest;
import com.yfmf.footlog.domain.club.enums.JoinRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    // 특정 회원이 특정 클럽에 특정 상태의 가입 요청이 존재하는지 확인
    boolean existsByMemberIdAndClubClubIdAndStatus(Long memberId, Long clubId, JoinRequestStatus status);

    List<JoinRequest> findByClubClubIdAndStatus(Long clubId, JoinRequestStatus status);

}
