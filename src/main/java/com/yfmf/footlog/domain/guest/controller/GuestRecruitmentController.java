package com.yfmf.footlog.domain.guest.controller;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.guest.dto.*;
import com.yfmf.footlog.domain.guest.enums.ApplicationStatus;
import com.yfmf.footlog.domain.guest.enums.RecruitmentStatus;
import com.yfmf.footlog.domain.guest.service.GuestRecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/guest-recruitments")
@Tag(name = "GuestRecruitment", description = "용병 모집 글 API")
@RequiredArgsConstructor
public class GuestRecruitmentController {
    private final GuestRecruitmentService guestRecruitmentService;

    /**
     * 용병 모집글 작성
     * @param logined
     * @param request
     * @return
     */
    @PostMapping
    @Operation(summary = "용병 모집글 작성", description = "구단이 용병 모집글을 작성합니다. 요청에는 모집 조건 및 내용을 포함해야 합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "모집글 생성 요청 데이터",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GuestRecruitmentCreateDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "모집글 생성 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "권한이 없습니다.")
            }
    )
    public ResponseEntity<GuestRecruitmentResponseDTO> createRecruitment(
            @AuthenticationPrincipal LoginedInfo logined,
            @Valid @RequestBody GuestRecruitmentCreateDTO request) {

        if (logined == null) {
            throw new LoginRequiredException("로그인이 필요합니다.", "GuestRecruitmentController.createRecruitment");
        }

        log.info("용병 모집글 작성 요청: userId={}", logined.getUserId());
        GuestRecruitmentResponseDTO response = guestRecruitmentService.createRecruitment(logined.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 용병 모집글 목록 조회
     * @param status
     * @return
     */
    @GetMapping
    @Operation(summary = "용병 모집글 목록 조회", description = "상태에 따라 모든 용병 모집글을 조회합니다. 상태를 전달하지 않으면 전체 목록을 조회합니다.",
            parameters = {
                    @Parameter(name = "status", description = "모집 상태 (e.g., RECRUITING, CLOSED)", required = false)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "모집글 조회 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    public ResponseEntity<List<GuestRecruitmentResponseDTO>> getAllRecruitments(
            @RequestParam(required = false) RecruitmentStatus status) {
        log.info("용병 모집글 목록 조회 요청");
        List<GuestRecruitmentResponseDTO> recruitments = guestRecruitmentService.getAllRecruitments(status);
        return ResponseEntity.ok(recruitments);
    }

    /**
     * 용병 모집글 상세 조회
     * @param recruitmentId
     * @return
     */
    @GetMapping("/{recruitmentId}")
    @Operation(summary = "용병 모집글 상세 조회", description = "특정 용병 모집글의 세부 정보를 조회합니다.",
            parameters = {
                    @Parameter(name = "recruitmentId", description = "모집글 ID", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "모집글 상세 조회 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "모집글을 찾을 수 없습니다.")
            }
    )
    public ResponseEntity<GuestRecruitmentDetailDTO> getRecruitment(
            @PathVariable Long recruitmentId) {
        log.info("용병 모집글 상세 조회 요청: recruitmentId={}", recruitmentId);
        GuestRecruitmentDetailDTO recruitment = guestRecruitmentService.getRecruitment(recruitmentId);
        return ResponseEntity.ok(recruitment);
    }

    /**
     * 용병 신청
     * @param recruitmentId
     * @param logined
     * @param request
     * @return
     */
    @PostMapping("/{recruitmentId}/applications")
    @Operation(summary = "용병 신청", description = "사용자가 특정 용병 모집글에 신청합니다.",
            parameters = {
                    @Parameter(name = "recruitmentId", description = "모집글 ID", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "용병 신청 데이터",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GuestApplicationCreateDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "용병 신청 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "모집글을 찾을 수 없습니다.")
            }
    )
    public ResponseEntity<GuestApplicationResponseDTO> applyForRecruitment(
            @PathVariable Long recruitmentId,
            @AuthenticationPrincipal LoginedInfo logined,
            @Valid @RequestBody GuestApplicationCreateDTO request) {

        if (logined == null) {
            throw new LoginRequiredException("로그인이 필요합니다.", "GuestRecruitmentController.applyForRecruitment");
        }

        log.info("용병 신청 요청: recruitmentId={}, userId={}", recruitmentId, logined.getUserId());
        GuestApplicationResponseDTO response =
                guestRecruitmentService.applyForRecruitment(recruitmentId, logined.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 용병 신청 상태 변경
     * @param applicationId
     * @param logined
     * @param status
     * @return
     */
    @PutMapping("/applications/{applicationId}/status")
    @Operation(summary = "용병 신청 상태 변경", description = "관리자가 특정 용병 신청의 상태를 승인 또는 거절로 변경합니다.",
            parameters = {
                    @Parameter(name = "applicationId", description = "용병 신청 ID", required = true),
                    @Parameter(name = "status", description = "변경할 상태 (APPROVED/REJECTED)", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상태 변경 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "권한이 없습니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "신청을 찾을 수 없습니다.")
            }
    )
    public ResponseEntity<GuestApplicationResponseDTO> updateApplicationStatus(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal LoginedInfo logined,
            @RequestParam ApplicationStatus status) {

        if (logined == null) {
            throw new LoginRequiredException("로그인이 필요합니다.", "GuestRecruitmentController.updateApplicationStatus");
        }

        log.info("용병 신청 상태 변경 요청: applicationId={}, status={}", applicationId, status);
        GuestApplicationResponseDTO response =
                guestRecruitmentService.updateApplicationStatus(applicationId, logined.getUserId(), status);
        return ResponseEntity.ok(response);
    }

    /**
     * 내 용병 신청 목록 조회
     * @param logined
     * @return
     */
    @GetMapping("/applications/my")
    @Operation(summary = "내 용병 신청 목록 조회", description = "사용자가 작성한 모든 용병 신청 목록을 조회합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "신청 목록 조회 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인이 필요합니다.")
            }
    )
    public ResponseEntity<List<GuestApplicationResponseDTO>> getMyApplications(
            @AuthenticationPrincipal LoginedInfo logined) {

        if (logined == null) {
            throw new LoginRequiredException("로그인이 필요합니다.", "GuestRecruitmentController.getMyApplications");
        }

        List<GuestApplicationResponseDTO> applications =
                guestRecruitmentService.getApplicationsByUserId(logined.getUserId());
        return ResponseEntity.ok(applications);
    }

    /**
     * 용병 신청자 목록 조회
     * @param recruitmentId
     * @param logined
     * @return
     */
    @GetMapping("/{recruitmentId}/applications")
    @Operation(summary = "용병 신청자 목록 조회", description = "특정 모집글에 지원한 모든 신청자를 조회합니다.",
            parameters = {
                    @Parameter(name = "recruitmentId", description = "모집글 ID", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "신청자 목록 조회 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "권한이 없습니다.")
            }
    )
    public ResponseEntity<List<GuestApplicationResponseDTO>> getRecruitmentApplications(
            @PathVariable Long recruitmentId,
            @AuthenticationPrincipal LoginedInfo logined) {

        if (logined == null) {
            throw new LoginRequiredException("로그인이 필요합니다.", "GuestRecruitmentController.getRecruitmentApplications");
        }

        List<GuestApplicationResponseDTO> applications =
                guestRecruitmentService.getApplicationsByRecruitmentId(recruitmentId, logined.getUserId());
        return ResponseEntity.ok(applications);
    }
}
