package com.yfmf.footlog.domain.club.service;

import com.yfmf.footlog.domain.club.entity.ClubMember;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.repository.ClubMemberRepository;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 구단이 존재하는지 확인
        if (!clubRepository.existsById(clubId)) {
            throw new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] joinClub");
        }

        // 이미 구단에 가입된 회원인지 확인
        if (clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)) {
            throw new IllegalArgumentException("이미 구단에 가입된 회원입니다.");
        }

        // 구단원 추가
        ClubMember clubMember = new ClubMember(clubId, userId);
        clubMemberRepository.save(clubMember);
    }

    /**
     * 구단원 탈퇴
     */
    @Transactional
    public void leaveClub(Long userId, Long clubId) {
        // 구단이 존재하는지 확인
        if (!clubRepository.existsById(clubId)) {
            throw new ClubNotFoundException("구단을 찾을 수 없습니다.", "[ClubMemberService] leaveClub");
        }

        // 구단원 탈퇴
        if (!clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)) {
            throw new IllegalArgumentException("해당 회원은 구단에 가입되어 있지 않습니다.");
        }

        clubMemberRepository.deleteByMemberIdAndClubId(userId, clubId);
    }
}
