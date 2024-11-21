package com.yfmf.footlog.domain.guest.service;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import com.yfmf.footlog.domain.club.service.ClubMemberService;
import com.yfmf.footlog.domain.guest.dto.*;
import com.yfmf.footlog.domain.guest.entity.GuestApplication;
import com.yfmf.footlog.domain.guest.entity.GuestRecruitment;
import com.yfmf.footlog.domain.guest.enums.ApplicationStatus;
import com.yfmf.footlog.domain.guest.enums.RecruitmentStatus;
import com.yfmf.footlog.domain.guest.exception.InvalidTimeException;
import com.yfmf.footlog.domain.guest.exception.RecruitmentNotFoundException;
import com.yfmf.footlog.domain.guest.exception.UnauthorizedException;
import com.yfmf.footlog.domain.guest.repository.GuestApplicationRepository;
import com.yfmf.footlog.domain.guest.repository.GuestRecruitmentRepository;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
    private final ClubMemberService clubMemberService;

    /**
     * 새로운 게스트 모집 글을 생성합니다.
     *
     * @param userId 작성자 회원 ID
     * @param dto    모집 글 생성 DTO
     * @return 생성된 모집 글의 응답 DTO
     */
    @Transactional
    public GuestRecruitmentResponseDTO createRecruitment(Long userId, GuestRecruitmentCreateDTO dto) {
        // 구단 존재 여부 및 권한 확인
        Club club = clubRepository.findById(dto.getClubId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구단입니다."));

        if (!hasClubAuthority(userId, club.getClubId())) {
            throw new IllegalArgumentException("구단 모집글을 작성할 권한이 없습니다.");
        }

        GuestRecruitment recruitment = GuestRecruitment.builder()
                .title(dto.getTitle())
                .userId(userId)
                .clubId(dto.getClubId())
                .matchDate(dto.getMatchDate())
                .matchStartTime(dto.getMatchStartTime())
                .matchEndTime(dto.getMatchEndTime())
                .location(dto.getLocation())
                .requiredNumber(dto.getRequiredNumber())
                .requiredPositions(dto.getRequiredPositions())
                .pay(dto.getPay())
                .description(dto.getDescription())
                .build();

        GuestRecruitment savedRecruitment = recruitmentRepository.save(recruitment);
        return new GuestRecruitmentResponseDTO(savedRecruitment, club);
    }

    /**
     * 상태에 따라 모든 게스트 모집 글을 조회합니다.
     *
     * @param status 모집 상태 (선택적으로 지정 가능)
     * @return 모집 글 응답 DTO 리스트
     */
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

    /**
     * 게스트 모집 글에 지원합니다.
     *
     * @param recruitmentId 모집 글 ID
     * @param userId        지원자 회원 ID
     * @param dto           지원 정보 DTO
     * @return 생성된 지원 응답 DTO
     */
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

    /**
     * 게스트 모집 글 지원 상태를 업데이트합니다.
     *
     * @param applicationId 지원 ID
     * @param userId        관리자 회원 ID
     * @param status        업데이트할 상태
     * @return 업데이트된 지원 응답 DTO
     */
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

    /**
     * 모집 글의 세부 정보를 조회합니다.
     *
     * @param recruitmentId 모집 글 ID
     * @return 모집 글 세부 응답 DTO
     */
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

    /**
     * 특정 사용자가 작성한 모든 지원 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 지원 응답 DTO 리스트
     */
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

    /**
     * 모집 글에 대한 모든 지원 목록을 조회합니다.
     *
     * @param recruitmentId 모집 글 ID
     * @param requestUserId 요청 사용자 ID
     * @return 지원 응답 DTO 리스트
     */
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

    @Transactional
    public GuestRecruitmentResponseDTO updateRecruitment(Long recruitmentId, GuestRecruitmentUpdateDTO request, Long userId) {

        log.info("용병 모집글 수정 시작 - ID: {}, UserId: {}", recruitmentId, userId);
        log.info("수정 요청 데이터: {}", request.toString());

        GuestRecruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RecruitmentNotFoundException("해당 모집글을 찾을 수 없습니다."));

        if (!hasUpdateAuthority(recruitment, userId)) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        validateMatchTime(request.getMatchStartTime(), request.getMatchEndTime());

        // 수정 전 상태 로깅
        log.info("수정 전 모집글 상태: {}", recruitment);

        // 모집글 수정
        recruitment.updateRecruitment(
                request.getTitle(),
                request.getMatchDate(),
                LocalTime.parse(request.getMatchStartTime()),
                LocalTime.parse(request.getMatchEndTime()),
                request.getLocation(),
                request.getRequiredNumber(),
                request.getRequiredPositions(),
                request.getPay(),
                request.getDescription()
        );

        // 수정 후 상태 로깅
        log.info("수정 후 모집글 상태: {}", recruitment);

        // 명시적 저장
        GuestRecruitment savedRecruitment = recruitmentRepository.saveAndFlush(recruitment);

        log.info("용병 모집글 수정 완료 - ID: {}", recruitmentId);

        return new GuestRecruitmentResponseDTO(savedRecruitment);
    }

    private boolean hasUpdateAuthority(GuestRecruitment recruitment, Long userId) {
        return recruitment.getMatchEnrollUserId().equals(userId) ||
                clubMemberService.hasClubPermission(userId, recruitment.getClubId());
    }

    private void validateMatchTime(String startTime, String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);

        if (start.isAfter(end) || start.equals(end)) {
            throw new InvalidTimeException("종료 시간은 시작 시간보다 이후여야 합니다.");
        }

        long durationInMinutes = ChronoUnit.MINUTES.between(start, end);
    }
}