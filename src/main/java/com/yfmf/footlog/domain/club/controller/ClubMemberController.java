package com.yfmf.footlog.domain.club.controller;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.club.dto.ClubMemberResponseDTO;
import com.yfmf.footlog.domain.club.entity.ClubMemberRole;
import com.yfmf.footlog.domain.club.service.ClubMemberService;
import com.yfmf.footlog.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자가 성공적으로 구단에 가입되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 401, \"errorType\": \"Unauthorized\", \"message\": \"로그인이 필요합니다.\"}"
                    )
            )),
            @ApiResponse(responseCode = "409", description = "사용자가 이미 구단에 가입되어 있습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 409, \"errorType\": \"Conflict\", \"message\": \"사용자가 이미 구단에 가입되어 있습니다.\"}"
                    )
            ))
    })
    @PostMapping("/{clubId}/join")
    public ResponseEntity<ClubMemberResponseDTO> joinClub(@PathVariable Long clubId, @AuthenticationPrincipal LoginedInfo logined) {
        if (logined == null) {
            log.error("[ClubMemberController] 로그인되지 않은 사용자가 구단에 가입을 시도했습니다.");
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubMemberController] joinClub");
        }

        log.info("[ClubMemberController] 사용자 {}가 구단 {}에 가입을 시도합니다.", logined.getUserId(), clubId);
        clubMemberService.joinClub(logined.getUserId(), clubId);

        ClubMemberResponseDTO responseDTO = new ClubMemberResponseDTO(
                logined.getUserId(),
                clubId,
                clubMemberService.getClubNameById(clubId),  // 구단 이름 가져오기
                "가입 성공"
        );

        log.info("[ClubMemberController] 사용자 {}가 구단 {}에 성공적으로 가입했습니다.", logined.getUserId(), clubId);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * 구단원 탈퇴
     */
    @Operation(summary = "구단원 탈퇴", description = "사용자가 구단에서 탈퇴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자가 성공적으로 구단에서 탈퇴되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 401, \"errorType\": \"Unauthorized\", \"message\": \"로그인이 필요합니다.\"}"
                    )
            )),
            @ApiResponse(responseCode = "404", description = "사용자가 구단에 가입되어 있지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"해당 사용자는 구단에 가입되어 있지 않습니다.\"}"
                    )
            ))
    })
    @DeleteMapping("/{clubId}/leave")
    public ResponseEntity<ClubMemberResponseDTO> leaveClub(@PathVariable Long clubId, @AuthenticationPrincipal LoginedInfo logined) {
        if (logined == null) {
            log.error("[ClubMemberController] 로그인되지 않은 사용자가 구단 탈퇴를 시도했습니다.");
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubMemberController] leaveClub");
        }

        log.info("[ClubMemberController] 사용자 {}가 클럽 {}에서 탈퇴를 시도합니다.", logined.getUserId(), clubId);
        clubMemberService.leaveClub(logined.getUserId(), clubId);

        ClubMemberResponseDTO responseDTO = new ClubMemberResponseDTO(
                logined.getUserId(),
                clubId,
                clubMemberService.getClubNameById(clubId),  // 구단 이름 가져오기
                "탈퇴 성공"
        );

        log.info("[ClubMemberController] 사용자 {}가 클럽 {}에서 성공적으로 탈퇴했습니다.", logined.getUserId(), clubId);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * 구단원 목록 조회
     */
    @Operation(summary = "구단원 목록 조회", description = "클럽에 소속된 구단원들의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구단원 목록이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 401, \"errorType\": \"Unauthorized\", \"message\": \"로그인이 필요합니다.\"}"
                    )
            )),
            @ApiResponse(responseCode = "404", description = "클럽이 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"클럽이 존재하지 않습니다.\"}"
                    )
            ))
    })
    @GetMapping("/{clubId}/members")
    public ResponseEntity<List<ClubMemberResponseDTO>> getClubMembers(@PathVariable Long clubId, @AuthenticationPrincipal LoginedInfo logined) {
        if (logined == null) {
            log.error("[ClubMemberController] 로그인되지 않은 사용자가 구단원을 조회하려고 시도했습니다.");
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubMemberController] getClubMembers");
        }

        log.info("[ClubMemberController] 클럽 ID={}의 구단원 목록을 조회합니다.", clubId);
        List<ClubMemberResponseDTO> members = clubMemberService.getClubMembers(clubId).stream()
                .map(member -> new ClubMemberResponseDTO(
                        member.getId(),
                        clubId,
                        clubMemberService.getClubNameById(clubId),
                        "조회 성공"
                ))
                .collect(Collectors.toList());

        log.info("[ClubMemberController] 클럽 ID={}의 구단원 목록 조회에 성공했습니다.", clubId);
        return ResponseEntity.ok(members);
    }

    /**
     * 구단원 역할 수정
     */
    @Operation(summary = "구단원 역할 수정", description = "구단원의 등급을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구단원의 역할이 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "구단원이 존재하지 않습니다.")
    })
    @PutMapping("/{clubId}/{userId}/role")
    public ResponseEntity<String> updateMemberRole(
            @PathVariable Long clubId,
            @PathVariable Long userId,
            @RequestParam ClubMemberRole role,
            @AuthenticationPrincipal LoginedInfo logined) {

        if (logined == null) {
            log.error("[ClubMemberController] 로그인되지 않은 사용자가 구단원 역할 수정을 시도했습니다.");
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        log.info("[ClubMemberController] 구단 ID={}의 사용자 ID={}의 역할을 {}로 수정 시도", clubId, userId, role);

        clubMemberService.updateMemberRole(clubId, userId, role, logined.getUserId());

        return ResponseEntity.ok("구단원의 역할이 성공적으로 수정되었습니다.");
    }
}