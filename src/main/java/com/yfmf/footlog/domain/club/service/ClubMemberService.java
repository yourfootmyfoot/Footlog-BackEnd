package com.yfmf.footlog.domain.club.service;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.entity.ClubMember;
import com.yfmf.footlog.domain.club.enums.ClubMemberRole;
import com.yfmf.footlog.domain.club.exception.ClubAlreadyJoinedException;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.repository.ClubMemberRepository;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClubMemberService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ClubMemberService(ClubRepository clubRepository, ClubMemberRepository clubMemberRepository, MemberRepository memberRepository) {
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 구단원 가입
     */
    @Transactional
    public void joinClub(Long userId, Long clubId) {
        log.info("[ClubMemberService] 구단 ID={}에 사용자 ID={}를 추가하려고 합니다.", clubId, userId);

        // 구단이 존재하는지 확인
        if (!clubRepository.existsById(clubId)) {
            throw new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] joinClub");
        }

        // 이미 구단에 가입된 회원인지 확인
        if (clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)) {
            log.error("[ClubMemberService] 사용자 ID={}는 이미 구단 ID={}에 가입되어 있습니다.", userId, clubId);
            // 409 Conflict 에러를 던지도록 IllegalArgumentException 대신 Custom Exception 사용
            throw new ClubAlreadyJoinedException("사용자가 이미 구단에 가입되어 있습니다.", "[ClubMemberService] joinClub");
        }

        // 구단원 추가
        ClubMember clubMember = new ClubMember(clubId, userId, ClubMemberRole.MEMBER);
        clubMemberRepository.save(clubMember);
        log.info("[ClubMemberService] 사용자 ID={}가 구단 ID={}에 성공적으로 가입되었습니다.", userId, clubId);
    }

    /**
     * 구단원 탈퇴
     */
    @Transactional
    public void leaveClub(Long userId, Long clubId) {
        log.info("[ClubMemberService] 구단 ID={}에서 사용자 ID={}를 탈퇴시키려고 합니다.", clubId, userId);


        // 구단이 존재하는지 확인
        if (!clubRepository.existsById(clubId)) {
            log.error("[ClubMemberService] 구단 ID={}가 존재하지 않습니다.", clubId);
            throw new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] leaveClub");
        }

        // 구단원 탈퇴
        if (!clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)) {
            log.error("[ClubMemberService] 사용자 ID={}는 구단 ID={}에 가입되어 있지 않습니다.", userId, clubId);
            throw new IllegalArgumentException("해당 회원은 구단에 가입되어 있지 않습니다.");
        }

        clubMemberRepository.deleteByMemberIdAndClubId(userId, clubId);
        log.info("[ClubMemberService] 사용자 ID={}가 구단 ID={}에서 성공적으로 탈퇴되었습니다.", userId, clubId);
    }

    public String getClubNameById(Long clubId) {
        return clubRepository.findById(clubId)
                .map(Club::getClubName)
                .orElseThrow(() -> new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] getClubNameById"));
    }

    /**
     * 구단에 소속된 구단원 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Member> getClubMembers(Long clubId) {
        log.info("[ClubMemberService] 구단 ID={}의 구단원을 조회합니다.", clubId);

        // 구단이 존재하는지 확인
        if (!clubRepository.existsById(clubId)) {
            log.error("[ClubMemberService] 구단 ID={}가 존재하지 않습니다.", clubId);
            throw new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] getClubMembers");
        }

        // 구단에 속한 구단원 조회
        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId);

        // 구단원 ID로 회원 정보 조회
        List<Member> members = clubMembers.stream()
                .map(clubMember -> memberRepository.findById(clubMember.getMemberId())
                        .orElseThrow(() -> new IllegalArgumentException("구단원의 회원 정보를 찾을 수 없습니다."))
                )
                .collect(Collectors.toList());

        log.info("[ClubMemberService] 구단 ID={}에 소속된 구단원을 성공적으로 조회했습니다.", clubId);
        return members;
    }

    /**
     * 구단원 등급 수정
     */
    @Transactional
    public void updateMemberRole(Long clubId, Long userId, ClubMemberRole newRole, Long requestingUserId) {
        log.info("[ClubMemberService] 구단 ID={}의 사용자 ID={}의 역할을 {}로 수정하려고 합니다.", clubId, userId, newRole);

        // 구단이 존재하는지 확인
        if (!clubRepository.existsById(clubId)) {
            log.error("[ClubMemberService] 구단 ID={}가 존재하지 않습니다.", clubId);
            throw new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] updateMemberRole");
        }

        // 요청한 사용자가 구단주인지 확인
        ClubMember requestingMember = clubMemberRepository.findByMemberIdAndClubId(requestingUserId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("구단주 정보를 찾을 수 없습니다."));
        if (requestingMember.getRole() != ClubMemberRole.OWNER) {
            log.error("[ClubMemberService] 사용자 ID={}는 구단주가 아닙니다.", requestingUserId);
            throw new IllegalArgumentException("권한이 없습니다. 구단주만 등급을 변경할 수 있습니다.");
        }

        // 등급 수정 대상 구단원 조회
        ClubMember clubMember = clubMemberRepository.findByMemberIdAndClubId(userId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 구단원을 찾을 수 없습니다."));

        // 일반 구단원을 매니저로 승격하거나, 매니저를 다시 일반 구단원으로 전환할 수 있음
        if (clubMember.getRole() == ClubMemberRole.MEMBER && newRole == ClubMemberRole.MANAGER) {
            clubMember.setRole(ClubMemberRole.MANAGER);
        } else if (clubMember.getRole() == ClubMemberRole.MANAGER && newRole == ClubMemberRole.MEMBER) {
            clubMember.setRole(ClubMemberRole.MEMBER);
        } else {
            throw new IllegalArgumentException("잘못된 역할 전환 요청입니다.");
        }

        clubMemberRepository.save(clubMember);
        log.info("[ClubMemberService] 사용자 ID={}의 역할이 {}로 성공적으로 수정되었습니다.", userId, newRole);
    }
}
