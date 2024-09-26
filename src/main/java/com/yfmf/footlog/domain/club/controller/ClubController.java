package com.yfmf.footlog.domain.club.controller;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.club.dto.ClubRegistRequestDTO;
import com.yfmf.footlog.domain.club.dto.ClubRegistResponseDTO;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.exception.ClubDuplicatedException;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.service.ClubService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clubs")
@Tag(name = "Club", description = "구단 API")
public class ClubController {

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    /**
     * 구단 등록
     */
    @Operation(summary = "구단 등록", description = "새로운 구단을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구단이 성공적으로 등록되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 401, \"errorType\": \"Unauthorized\", \"message\": \"로그인이 필요합니다.\"}"
                    )
            )),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 클럽 코드입니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 409, \"errorType\": \"Duplicated Club\", \"message\": \"이미 존재하는 클럽 코드입니다.\"}"
                    )
            ))
    })
    @PostMapping

    public ResponseEntity<ClubRegistResponseDTO> createClub(@RequestBody ClubRegistRequestDTO clubInfo,
                                                            @AuthenticationPrincipal LoginedInfo logined) {


        // 로그인된 사용자인지 확인
        if (logined == null) {
            log.error("[ClubController] 로그인되지 않은 사용자가 구단 등록을 시도했습니다.");
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[CourseWish] addCourseWish");
        }

        // 로그: 클럽 등록 요청
        log.info("[ClubController] 클럽 등록 요청: 클럽 이름={}, 클럽 코드={}", clubInfo.getClubName(), clubInfo.getClubCode());

        // 로그인된 사용자의 ID를 설정
        clubInfo.setUserId(logined.getUserId());
        System.out.println(clubInfo);
        try {
            ClubRegistResponseDTO responseDto = clubService.registClub(clubInfo);
            log.info("[ClubController] 클럽 등록 성공: 클럽 이름={}, 클럽 ID={}", responseDto.getClubName(), responseDto.getClubCode());
            return ResponseEntity.ok(responseDto);
        } catch (ClubDuplicatedException e) {
            log.error("[ClubController] 클럽 등록 실패 - 중복된 클럽 코드: 클럽 코드={}", clubInfo.getClubCode());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }


    /**
     * 모든 구단 조회
     */
    @Operation(summary = "모든 구단 조회", description = "등록된 모든 구단을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구단 목록이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "구단이 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"구단이 존재하지 않습니다.\"}"
                    )
            ))
    })
    @GetMapping
    public List<Club> getAllClubs() {
        log.info("[ClubController] 모든 클럽 조회 요청");
        List<Club> clubs = clubService.getAllClubs();
        log.info("[ClubController] 조회된 클럽 수: {}", clubs.size());
        return clubs;
    }


    /**
     * 구단주 아이디로 구단 조회
     */
    @Operation(summary = "구단주 아이디로 구단 조회", description = "구단주의 아이디를 사용하여 구단을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구단 목록이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "구단이 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"구단이 존재하지 않습니다.\"}"
                    )
            ))
    })
    @GetMapping("/owner/{userId}")
    public ResponseEntity<List<Club>> getClubsByUserId(@PathVariable Long userId) {
        log.info("[ClubController] 구단주 ID={}에 대한 클럽 조회 요청", userId);
        try {
            List<Club> clubs = clubService.getClubsByUserId(userId);
            log.info("[ClubController] 조회된 클럽 수: {}", clubs.size());
            return ResponseEntity.ok(clubs);
        } catch (ClubNotFoundException e) {
            log.warn("[ClubController] 구단주 ID={}에 대한 클럽이 존재하지 않음", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * 구단 ID로 구단 상세 조회
     */
    @Operation(summary = "구단 ID로 구단 조회", description = "구단 ID를 사용하여 구단의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구단이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "구단이 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"구단이 존재하지 않습니다.\"}"
                    )
            ))
    })
    @GetMapping("/{clubId}")
    public ResponseEntity<Club> getClubById(@PathVariable Long clubId, @AuthenticationPrincipal LoginedInfo logined) {

        log.info("[ClubController] 클럽 ID={}에 대한 조회 요청", clubId);

        // 로그인된 사용자인지 확인
        if (logined == null) {
            log.error("[ClubController] 로그인되지 않은 사용자가 클럽 조회를 시도했습니다.");
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubController] getClubById");
        }

        try {
            Club club = clubService.getClubByClubId(clubId);
            log.info("[ClubController] 클럽 조회 성공: 클럽 ID={}", clubId);
            return ResponseEntity.ok(club);
        } catch (ClubNotFoundException e) {
            log.error("[ClubController] 클럽 조회 실패 - 클럽이 존재하지 않음: 클럽 ID={}", clubId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    /**
     * 구단 업데이트
     */
    @Operation(summary = "구단 업데이트", description = "구단 정보를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구단이 성공적으로 업데이트되었습니다."),
            @ApiResponse(responseCode = "404", description = "업데이트할 구단이 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"업데이트할 구단이 존재하지 않습니다.\"}"
                    )
            ))
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateClub(@PathVariable Long id, @RequestBody ClubRegistRequestDTO clubInfo, @AuthenticationPrincipal LoginedInfo logined) {

        log.info("[ClubController] 클럽 ID={}에 대한 업데이트 요청", id);

        // 로그인된 사용자인지 확인
        if (logined == null) {
            log.error("[ClubController] 로그인되지 않은 사용자가 클럽 업데이트를 시도했습니다.");
            throw new LoginRequiredException("로그인 후 이용이 가능합니다.", "[ClubController] updateClub");
        }

        try {
            clubService.updateClub(id, clubInfo);
            log.info("[ClubController] 클럽 업데이트 성공: 클럽 ID={}", id);
            return ResponseEntity.ok("구단이 성공적으로 업데이트되었습니다.");
        } catch (ClubNotFoundException e) {
            log.error("[ClubController] 클럽 업데이트 실패 - 클럽이 존재하지 않음: 클럽 ID={}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * 구단 삭제
     */
    @Operation(summary = "구단 삭제", description = "구단을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구단이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "삭제할 구단이 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"삭제할 구단이 존재하지 않습니다.\"}"
                    )
            ))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClub(@PathVariable Long id) {
        log.info("[ClubController] 클럽 ID={}에 대한 삭제 요청", id);

        try {
            clubService.deleteClub(id);
            log.info("[ClubController] 클럽 삭제 성공: 클럽 ID={}", id);
            return ResponseEntity.ok("구단이 성공적으로 삭제되었습니다.");
        } catch (ClubNotFoundException e) {
            log.error("[ClubController] 클럽 삭제 실패 - 클럽이 존재하지 않음: 클럽 ID={}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}