package com.yfmf.footlog.domain.guest.service;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import com.yfmf.footlog.domain.guest.dto.*;
import com.yfmf.footlog.domain.guest.entity.GuestApplication;
import com.yfmf.footlog.domain.guest.entity.GuestRecruitment;
import com.yfmf.footlog.domain.guest.enums.ApplicationStatus;
import com.yfmf.footlog.domain.guest.enums.RecruitmentStatus;
import com.yfmf.footlog.domain.guest.exception.RecruitmentNotFoundException;
import com.yfmf.footlog.domain.guest.repository.GuestApplicationRepository;
import com.yfmf.footlog.domain.guest.repository.GuestRecruitmentRepository;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuestRecruitmentService {
    private final GuestRecruitmentRepository recruitmentRepository;
    private final GuestApplicationRepository applicationRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public GuestRecruitmentResponseDTO createRecruitment(Long userId, GuestRecruitmentCreateDTO dto) {
        // 구단 존재 여부 및 권한 확인
        Club club = clubRepository.findById(dto.getClubId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구단입니다."));

        if (!hasClubAuthority(userId, club.getClubId())) {
            throw new IllegalArgumentException("구단 모집글을 작성할 권한이 없습니다.");
        }

        GuestRecruitment recruitment = GuestRecruitment.builder()
                .clubId(dto.getClubId())
                .matchDateTime(dto.getMatchDateTime())
                .location(dto.getLocation())
                .requiredNumber(dto.getRequiredNumber())
                .requiredPositions(dto.getRequiredPositions())
                .pay(dto.getPay())
                .description(dto.getDescription())
                .build();

        GuestRecruitment savedRecruitment = recruitmentRepository.save(recruitment);
        return new GuestRecruitmentResponseDTO(savedRecruitment, club);
    }

    public List<GuestRecruitmentResponseDTO> getAllRecruitments(RecruitmentStatus status) {
        List<GuestRecruitment> recruitments;
        if (status != null) {
            recruitments = recruitmentRepository.findByStatus(status);
        } else {
            recruitments = recruitmentRepository.findAll();
        }

        return recruitments.stream()
                .map(recruitment -> {
                    Club club = clubRepository.findById(recruitment.getClubId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구단입니다."));
                    return new GuestRecruitmentResponseDTO(recruitment, club);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public GuestApplicationResponseDTO applyForRecruitment(
            Long recruitmentId, Long userId, GuestApplicationCreateDTO dto) {

        GuestRecruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모집글입니다."));

        if (recruitment.getStatus() != RecruitmentStatus.RECRUITING) {
            throw new IllegalArgumentException("모집이 마감된 글입니다.");
        }

        // 중복 신청 체크
        if (applicationRepository.existsByRecruitmentIdAndApplicantId(recruitmentId, userId)) {
            throw new IllegalArgumentException("이미 신청한 모집글입니다.");
        }

        GuestApplication application = GuestApplication.builder()
                .recruitment(recruitment)
                .applicantId(userId)
                .applyPosition(dto.getApplyPosition())
                .message(dto.getMessage())
                .build();

        GuestApplication savedApplication = applicationRepository.save(application);

        Member applicant = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new GuestApplicationResponseDTO(savedApplication, applicant);
    }

    @Transactional
    public GuestApplicationResponseDTO updateApplicationStatus(
            Long applicationId, Long userId, ApplicationStatus status) {

        GuestApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청입니다."));

        // 구단 권한 체크
        if (!hasClubAuthority(userId, application.getRecruitment().getClubId())) {
            throw new IllegalArgumentException("신청 상태를 변경할 권한이 없습니다.");
        }

        application.updateStatus(status);

        Member applicant = memberRepository.findById(application.getApplicantId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new GuestApplicationResponseDTO(application, applicant);
    }

    private boolean hasClubAuthority(Long userId, Long clubId) {
        // 구단 권한 체크 로직 구현 (ClubService 활용)
        return true; // 임시 구현
    }

    @Transactional(readOnly = true)
    public GuestRecruitmentDetailDTO getRecruitment(Long recruitmentId) {
        // 모집글 조회
        GuestRecruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글입니다."));

        // 구단 정보 조회
        Club club = clubRepository.findById(recruitment.getClubId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구단입니다."));

        // 신청자 목록 조회
        List<GuestApplicationResponseDTO> applications = getApplicationsByRecruitmentId(recruitmentId, club.getUserId());

        return new GuestRecruitmentDetailDTO(recruitment, club, applications);
    }

    @Transactional(readOnly = true)
    public List<GuestApplicationResponseDTO> getApplicationsByUserId(Long userId) {
        List<GuestApplication> applications = applicationRepository.findByApplicantId(userId);

        return applications.stream()
                .map(application -> {
                    Member applicant = memberRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
                    return new GuestApplicationResponseDTO(application, applicant);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GuestApplicationResponseDTO> getApplicationsByRecruitmentId(Long recruitmentId, Long requestUserId) {
        // 모집글 조회 및 권한 확인
        GuestRecruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RecruitmentNotFoundException("존재하지 않는 모집글입니다."));

        // 구단 권한 확인 (구단주/매니저만 신청자 목록 조회 가능)
        if (!hasClubAuthority(requestUserId, recruitment.getClubId())) {
            throw new IllegalArgumentException("신청자 목록을 조회할 권한이 없습니다.");
        }

        List<GuestApplication> applications = applicationRepository.findByRecruitmentId(recruitmentId);

        return applications.stream()
                .map(application -> {
                    Member applicant = memberRepository.findById(application.getApplicantId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
                    return new GuestApplicationResponseDTO(application, applicant);
                })
                .sorted((a1, a2) -> {
                    // 대기중인 신청을 먼저 보여주고, 그 다음 승인된 신청, 마지막으로 거절된 신청 순으로 정렬
                    if (a1.getStatus() == a2.getStatus()) {
                        return a2.getCreatedAt().compareTo(a1.getCreatedAt());  // 같은 상태내에서는 최신순
                    }
                    return a1.getStatus().getOrder() - a2.getStatus().getOrder();
                })
                .collect(Collectors.toList());
    }
}