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

    @PostMapping
    @Operation(summary = "용병 지원글 작성", description = "사용자가 용병 지원글을 작성합니다.")
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

    @GetMapping
    @Operation(summary = "용병 지원글 목록 조회", description = "등록된 모든 용병 지원글을 조회합니다.")
    public ResponseEntity<List<GuestPostResponseDTO>> getAllGuestPosts(
            @RequestParam(required = false) PostStatus status) {
        log.info("용병 지원글 목록 조회 요청");
        List<GuestPostResponseDTO> posts = guestPostService.getAllGuestPosts(status);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "용병 지원글 상세 조회", description = "특정 용병 지원글을 상세 조회합니다.")
    public ResponseEntity<GuestPostDetailDTO> getGuestPost(@PathVariable Long postId) {
        log.info("용병 지원글 상세 조회 요청: postId={}", postId);
        GuestPostDetailDTO post = guestPostService.getGuestPost(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    @Operation(summary = "용병 지원글 수정", description = "용병 지원글을 수정합니다.")
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

    @DeleteMapping("/{postId}")
    @Operation(summary = "용병 지원글 삭제", description = "용병 지원글을 삭제합니다.")
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

