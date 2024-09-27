package com.yfmf.footlog.domain.club.service;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.entity.ClubMember;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.repository.ClubMemberRepository;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ClubMemberService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Autowired
    public ClubMemberService(ClubRepository clubRepository, ClubMemberRepository clubMemberRepository) {
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
    }

    /**
     * 구단원 가입
     */
    @Transactional
    public void joinClub(Long userId, Long clubId) {
        log.info("[ClubMemberService] 클럽 ID={}에 사용자 ID={}를 추가하려고 합니다.", clubId, userId);

        // 구단이 존재하는지 확인
        if (!clubRepository.existsById(clubId)) {
            log.error("[ClubMemberService] 클럽 ID={}가 존재하지 않습니다.", clubId);
            throw new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] joinClub");
        }

        // 이미 구단에 가입된 회원인지 확인
        if (clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)) {
            log.error("[ClubMemberService] 사용자 ID={}는 이미 클럽 ID={}에 가입되어 있습니다.", userId, clubId);
            throw new IllegalArgumentException("이미 구단에 가입된 회원입니다.");
        }

        // 구단원 추가
        ClubMember clubMember = new ClubMember(clubId, userId);
        clubMemberRepository.save(clubMember);
        log.info("[ClubMemberService] 사용자 ID={}가 클럽 ID={}에 성공적으로 가입되었습니다.", userId, clubId);
    }

    /**
     * 구단원 탈퇴
     */
    @Transactional
    public void leaveClub(Long userId, Long clubId) {
        log.info("[ClubMemberService] 클럽 ID={}에서 사용자 ID={}를 탈퇴시키려고 합니다.", clubId, userId);


        // 구단이 존재하는지 확인
        if (!clubRepository.existsById(clubId)) {
            log.error("[ClubMemberService] 클럽 ID={}가 존재하지 않습니다.", clubId);
            throw new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] leaveClub");
        }

        // 구단원 탈퇴
        if (!clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)) {
            log.error("[ClubMemberService] 사용자 ID={}는 클럽 ID={}에 가입되어 있지 않습니다.", userId, clubId);
            throw new IllegalArgumentException("해당 회원은 구단에 가입되어 있지 않습니다.");
        }

        clubMemberRepository.deleteByMemberIdAndClubId(userId, clubId);
        log.info("[ClubMemberService] 사용자 ID={}가 클럽 ID={}에서 성공적으로 탈퇴되었습니다.", userId, clubId);
    }

    public String getClubNameById(Long clubId) {
        return clubRepository.findById(clubId)
                .map(Club::getClubName)
                .orElseThrow(() -> new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] getClubNameById"));
    }

}
