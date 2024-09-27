package com.yfmf.footlog.domain.club.controller;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.club.service.ClubMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/club-members")
@Tag(name = "ClubMember", description = "구단원 API")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    @Autowired
    public ClubMemberController(ClubMemberService clubMemberService) {
        this.clubMemberService = clubMemberService;
    }

    /**
     * 구단원 가입
     */
    @Operation(summary = "구단원 가입", description = "사용자가 구단에 가입합니다.")
    @PostMapping("/{clubId}/join")
    public ResponseEntity<String> joinClub(@PathVariable Long clubId, @AuthenticationPrincipal LoginedInfo logined) {
        if (logined == null) {
            log.error("[ClubMemberController] 로그인되지 않은 사용자가 구단에 가입을 시도했습니다.");
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubMemberController] joinClub");
        }

        log.info("[ClubMemberController] 사용자 {}가 구단 {}에 가입을 시도합니다.", logined.getUserId(), clubId);
        clubMemberService.joinClub(logined.getUserId(), clubId);
        log.info("[ClubMemberController] 사용자 {}가 구단 {}에 성공적으로 가입했습니다.", logined.getUserId(), clubId);
        return ResponseEntity.ok("구단 가입 성공");
    }

    /**
     * 구단원 탈퇴
     */
    @Operation(summary = "구단원 탈퇴", description = "사용자가 구단에서 탈퇴합니다.")
    @DeleteMapping("/{clubId}/leave")
    public ResponseEntity<String> leaveClub(@PathVariable Long clubId, @AuthenticationPrincipal LoginedInfo logined) {
        if (logined == null) {
            log.error("[ClubMemberController] 로그인되지 않은 사용자가 구단 탈퇴를 시도했습니다.");
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubMemberController] leaveClub");
        }

        log.info("[ClubMemberController] 사용자 {}가 클럽 {}에서 탈퇴를 시도합니다.", logined.getUserId(), clubId);
        clubMemberService.leaveClub(logined.getUserId(), clubId);
        log.info("[ClubMemberController] 사용자 {}가 클럽 {}에서 성공적으로 탈퇴했습니다.", logined.getUserId(), clubId);

        return ResponseEntity.ok("구단 탈퇴 성공");
    }
}