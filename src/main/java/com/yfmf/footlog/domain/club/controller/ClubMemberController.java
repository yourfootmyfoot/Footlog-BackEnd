package com.yfmf.footlog.domain.club.controller;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.club.service.ClubMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubMemberController] joinClub");
        }

        clubMemberService.joinClub(logined.getUserId(), clubId);
        return ResponseEntity.ok("구단 가입 성공");
    }

    /**
     * 구단원 탈퇴
     */
    @Operation(summary = "구단원 탈퇴", description = "사용자가 구단에서 탈퇴합니다.")
    @DeleteMapping("/{clubId}/leave")
    public ResponseEntity<String> leaveClub(@PathVariable Long clubId, @AuthenticationPrincipal LoginedInfo logined) {
        if (logined == null) {
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubMemberController] leaveClub");
        }

        clubMemberService.leaveClub(logined.getUserId(), clubId);
        return ResponseEntity.ok("구단 탈퇴 성공");
    }
}