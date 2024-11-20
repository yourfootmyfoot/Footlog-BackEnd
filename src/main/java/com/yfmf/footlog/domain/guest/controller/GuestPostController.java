package com.yfmf.footlog.domain.guest.controller;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.guest.dto.GuestPostCreateDTO;
import com.yfmf.footlog.domain.guest.dto.GuestPostDetailDTO;
import com.yfmf.footlog.domain.guest.dto.GuestPostResponseDTO;
import com.yfmf.footlog.domain.guest.dto.GuestPostUpdateDTO;
import com.yfmf.footlog.domain.guest.enums.PostStatus;
import com.yfmf.footlog.domain.guest.service.GuestPostService;
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
@RequestMapping("/api/guest-posts")
@Tag(name = "GuestPost", description = "용병 지원 글 API")
@RequiredArgsConstructor
public class GuestPostController {
    private final GuestPostService guestPostService;

    /**
     * 용병 지원글 작성
     * @param logined
     * @param request
     * @return
     */
    @PostMapping
    @Operation(summary = "용병 지원글 작성", description = "사용자가 자신의 정보를 기반으로 용병 지원글을 작성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "용병 지원글 생성 요청 데이터",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GuestPostCreateDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "지원글 작성 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인이 필요합니다.")
            }
    )
    public ResponseEntity<GuestPostResponseDTO> createGuestPost(
            @AuthenticationPrincipal LoginedInfo logined,
            @Valid @RequestBody GuestPostCreateDTO request) {

        if (logined == null) {
            throw new LoginRequiredException("로그인이 필요합니다.", "GuestPostController.createGuestPost");
        }

        log.info("용병 지원글 작성 요청: userId={}", logined.getUserId());
        GuestPostResponseDTO response = guestPostService.createGuestPost(logined.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 용병 지원글 목록 조회
     * @param status
     * @return
     */
    @GetMapping
    @Operation(summary = "용병 지원글 목록 조회", description = "등록된 모든 용병 지원글을 상태에 따라 조회합니다. 상태를 전달하지 않으면 전체 목록을 반환합니다.",
            parameters = {
                    @Parameter(name = "status", description = "지원글 상태 (e.g., AVAILABLE, CLOSED)", required = false)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "지원글 목록 조회 성공")
            }
    )
    public ResponseEntity<List<GuestPostResponseDTO>> getAllGuestPosts(
            @RequestParam(required = false) PostStatus status) {
        log.info("용병 지원글 목록 조회 요청");
        List<GuestPostResponseDTO> posts = guestPostService.getAllGuestPosts(status);
        return ResponseEntity.ok(posts);
    }

    /**
     * 용병 지원글 상세 조회
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    @Operation(summary = "용병 지원글 상세 조회", description = "특정 지원글의 상세 정보를 조회합니다.",
            parameters = {
                    @Parameter(name = "postId", description = "지원글 ID", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "지원글 상세 조회 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "지원글을 찾을 수 없습니다.")
            }
    )
    public ResponseEntity<GuestPostDetailDTO> getGuestPost(@PathVariable Long postId) {
        log.info("용병 지원글 상세 조회 요청: postId={}", postId);
        GuestPostDetailDTO post = guestPostService.getGuestPost(postId);
        return ResponseEntity.ok(post);
    }

    /**
     * 용병 지원글 수정
     * @param postId
     * @param logined
     * @param request
     * @return
     */
    @PutMapping("/{postId}")
    @Operation(summary = "용병 지원글 수정", description = "사용자가 작성한 용병 지원글을 수정합니다.",
            parameters = {
                    @Parameter(name = "postId", description = "지원글 ID", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "용병 지원글 수정 데이터",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GuestPostUpdateDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "지원글 수정 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "수정 권한이 없습니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "지원글을 찾을 수 없습니다.")
            }
    )
    public ResponseEntity<GuestPostResponseDTO> updateGuestPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal LoginedInfo logined,
            @Valid @RequestBody GuestPostUpdateDTO request) {

        if (logined == null) {
            throw new LoginRequiredException("로그인이 필요합니다.", "GuestPostController.updateGuestPost");
        }

        log.info("용병 지원글 수정 요청: postId={}, userId={}", postId, logined.getUserId());
        GuestPostResponseDTO response = guestPostService.updateGuestPost(postId, logined.getUserId(), request);
        return ResponseEntity.ok(response);
    }

    /**
     * 용병 지원글 삭제
     * @param postId
     * @param logined
     * @return
     */
    @DeleteMapping("/{postId}")
    @Operation(summary = "용병 지원글 삭제", description = "사용자가 작성한 용병 지원글을 삭제합니다.",
            parameters = {
                    @Parameter(name = "postId", description = "지원글 ID", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "지원글 삭제 성공"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "삭제 권한이 없습니다."),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "지원글을 찾을 수 없습니다.")
            }
    )
    public ResponseEntity<Void> deleteGuestPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal LoginedInfo logined) {

        if (logined == null) {
            throw new LoginRequiredException("로그인이 필요합니다.", "GuestPostController.deleteGuestPost");
        }

        log.info("용병 지원글 삭제 요청: postId={}, userId={}", postId, logined.getUserId());
        guestPostService.deleteGuestPost(postId, logined.getUserId());
        return ResponseEntity.noContent().build();
    }
}

