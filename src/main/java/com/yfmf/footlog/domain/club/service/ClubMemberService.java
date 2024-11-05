package com.yfmf.footlog.domain.club.service;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.entity.ClubMember;
import com.yfmf.footlog.domain.club.entity.JoinRequest;
import com.yfmf.footlog.domain.club.enums.ClubMemberRole;
import com.yfmf.footlog.domain.club.enums.JoinRequestStatus;
import com.yfmf.footlog.domain.club.exception.ClubAlreadyJoinedException;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.exception.DuplicateJoinRequestException;
import com.yfmf.footlog.domain.club.repository.ClubMemberRepository;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import com.yfmf.footlog.domain.club.repository.JoinRequestRepository;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ClubMemberService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final MemberRepository memberRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final ClubService clubService;


    /**
     * 구단원 가입요청
     */
    @Transactional
    public void requestJoinClub(Long userId, Long clubId) {
        log.info("[ClubMemberService] 구단 ID={}에 사용자 ID={}를 추가하려고 합니다.", clubId, userId);

        // 구단이 존재하는지 확인
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] joinClub"));

        // 이미 구단에 가입된 회원인지 확인
        if (clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)) {
            log.error("[ClubMemberService] 사용자 ID={}는 이미 구단 ID={}에 가입되어 있습니다.", userId, clubId);
            // 409 Conflict 에러를 던지도록 IllegalArgumentException 대신 Custom Exception 사용
            throw new ClubAlreadyJoinedException("사용자가 이미 구단에 가입되어 있습니다.", "[ClubMemberService] joinClub");
        }

        // 가입 요청이 이미 존재하는지 확인하는 로직
        boolean isAlreadyRequested = clubMemberRepository.existsByMemberIdAndClubId(userId, clubId);
        if (isAlreadyRequested) {
            throw new DuplicateJoinRequestException();
        }

        // 가입 요청 생성
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        JoinRequest joinRequest = new JoinRequest(club, member);
        joinRequestRepository.save(joinRequest);

        log.info("[ClubMemberService] 사용자 ID={}의 클럽 ID={} 가입 요청이 성공적으로 저장되었습니다.", userId, clubId);
    }

    /**
     * 구단원 가입승인
     */
    @Transactional
    public void approveJoinRequest(Long clubId, Long requestId, Long requestingUserId) {
        log.info("[ClubMemberService] 구단 ID={}의 가입 요청 ID={} 승인 시도", clubId, requestId);

        // 구단주 또는 매니저 권한 확인
        ClubMember requester = clubMemberRepository.findByMemberIdAndClubId(requestingUserId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("권한이 없습니다."));
        if (requester.getRole() != ClubMemberRole.OWNER && requester.getRole() != ClubMemberRole.MANAGER) {
            throw new IllegalArgumentException("권한이 없습니다. 승인 권한이 없습니다.");
        }

        // 가입 요청 조회 및 승인 처리
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("가입 요청을 찾을 수 없습니다."));
        joinRequest.setStatus(JoinRequestStatus.APPROVED);

        // 구단원 추가
        ClubMember clubMember = new ClubMember(clubId, joinRequest.getMember().getId(), ClubMemberRole.MEMBER);
        clubMemberRepository.save(clubMember);

        // 구단원 수 업데이트
        clubService.updateMemberCount(clubId);
    }

    /**
     * 구단원 가입거절
     */
    @Transactional
    public void rejectJoinRequest(Long clubId, Long requestId, Long requestingUserId) {
        log.info("[ClubMemberService] 구단 ID={}의 가입 요청 ID={} 거절 시도", clubId, requestId);

        // 구단주 또는 매니저 권한 확인
        ClubMember requester = clubMemberRepository.findByMemberIdAndClubId(requestingUserId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("권한이 없습니다."));
        if (requester.getRole() != ClubMemberRole.OWNER && requester.getRole() != ClubMemberRole.MANAGER) {
            throw new IllegalArgumentException("권한이 없습니다. 거절 권한이 없습니다.");
        }

        // 가입 요청 조회 및 거절 처리
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("가입 요청을 찾을 수 없습니다."));
        joinRequest.setStatus(JoinRequestStatus.REJECTED);
        joinRequestRepository.save(joinRequest);

        log.info("[ClubMemberService] 구단 ID={}의 가입 요청 ID={}이 성공적으로 거절되었습니다.", clubId, requestId);
    }

    /**
     * 구단원 탈퇴
     */
    @Transactional
    public void leaveClub(Long userId, Long clubId) {
        log.info("[ClubMemberService] 사용자 ID={}가 구단 ID={}에서 탈퇴하려고 합니다.", userId, clubId);


        // 구단이 존재하는지 확인
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] leaveClub"));

        // 구단 가입유무 확인
        if (!clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)) {
            log.error("[ClubMemberService] 사용자 ID={}는 구단 ID={}에 가입되어 있지 않습니다.", userId, clubId);
            throw new IllegalArgumentException("해당 회원은 구단에 가입되어 있지 않습니다.");
        }

        // 구단원 삭제
        clubMemberRepository.deleteByMemberIdAndClubId(userId, clubId);

        // 구단원 수 업데이트
        clubService.updateMemberCount(clubId);

        log.info("[ClubMemberService] 사용자 ID={}가 구단 ID={}에서 성공적으로 탈퇴하였으며, 구단원 수가 {}로 업데이트되었습니다.", userId, clubId, club.getMemberCount());
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

        // 동일한 역할로의 전환 요청에 대한 검증
        if (clubMember.getRole() == newRole) {
            throw new IllegalArgumentException("해당 사용자는 이미 " + newRole + " 등급을 가지고 있습니다.");
        }

        // 등급 변경 처리
        clubMember.setRole(newRole);
        clubMemberRepository.save(clubMember);

        log.info("[ClubMemberService] 사용자 ID={}의 역할이 {}로 성공적으로 수정되었습니다.", userId, newRole);
    }


    /**
     * 구단원 여부 확인
     */
    public boolean isClubMember(Long userId, Long clubId) {
        return clubMemberRepository.existsByMemberIdAndClubId(userId, clubId);
    }

    /**
     * 구단원 권한 확인
     */
    public boolean hasClubPermission(Long userId, Long clubId) {
        return clubMemberRepository.findByMemberIdAndClubId(userId, clubId)
                .map(member -> member.getRole() == ClubMemberRole.OWNER || member.getRole() == ClubMemberRole.MANAGER)
                .orElse(false); // 구단원이 아닌 경우 false 반환
    }

    /**
     * 특정 구단의 가입 요청 목록을 조회
     */
    @Transactional(readOnly = true)
    public List<JoinRequest> getJoinRequestsByClubId(Long clubId) {
        log.info("[ClubMemberService] 구단 ID={}의 가입 요청 목록을 조회합니다.", clubId);
        return joinRequestRepository.findByClubClubIdAndStatus(clubId, JoinRequestStatus.PENDING);
    }

    /**
     * 특정 회원의 클럽 내 역할 조회
     */
    @Transactional(readOnly = true)
    public String getMemberRole(Long clubId, Long userId) {
        log.info("[ClubMemberService] 구단 ID={}의 사용자 ID={}의 역할을 조회합니다.", clubId, userId);

        // 구단과 회원이 존재하는지 확인
        ClubMember clubMember = clubMemberRepository.findByMemberIdAndClubId(userId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 구단원의 정보를 찾을 수 없습니다."));

        log.info("[ClubMemberService] 사용자 ID={}의 역할은 {}입니다.", userId, clubMember.getRole());
        return clubMember.getRole().name();
    }
}
