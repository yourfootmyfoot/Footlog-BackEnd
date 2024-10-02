package com.yfmf.footlog.domain.match.controller;

import com.yfmf.footlog.domain.match.dto.MatchRegisterRequestDTO;
import com.yfmf.footlog.domain.match.dto.MatchResponseDTO;
import com.yfmf.footlog.domain.match.service.MatchService;
import com.yfmf.footlog.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Match API", description = "매치 관리 API")
@RequestMapping("/api/v1/matches")
public class MatchController {

    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // 매치 id로 매치 정보 반환
    @GetMapping("/detail/{matchId}")
    public MatchResponseDTO findAllMatches(@PathVariable Long matchId) {
        return matchService.findMatchByMatchId(matchId);
    }

    // 모든 경기 조회
    @Operation(summary = "모든 경기 조회", description = "등록된 모든 경기를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경기 목록이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "경기목록이 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"경기 목록이 존재하지 않습니다.\"}"
                    )
            ))
    })
    @GetMapping
    public ResponseEntity<List<MatchResponseDTO>> getAllMatches() {
        List<MatchResponseDTO> matches = matchService.findAllMatches();
        return ResponseEntity.ok(matches);
    }

    // 단일 경기 조회
    @Operation(summary = "단일 경기 조회", description = "등록된 단일 경기를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록된 경기를 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "등록된 경기가 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"등록된 경기 정보가 존재하지 않습니다.\"}"
                    )
            ))
    })
    @GetMapping("/{matchId}")
    public ResponseEntity<MatchResponseDTO> getMatchById(@PathVariable Long matchId) {
        MatchResponseDTO match = matchService.findMatchByMatchId(matchId);
        return ResponseEntity.ok(match);
    }

    // 경기 등록
    @Operation(summary = "경기 등록", description = "경기를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "경기가 성공적으로 등록되었습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MatchResponseDTO.class),
                    examples = @ExampleObject(
                            value = "{\"matchId\": 1, \"matchIntroduce\": \"Friendly match\", \"matchStatus\": \"PENDING\", \"fieldLocation\": \"Seoul\", ...}"
                    )
            )),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다. 입력 데이터를 확인해주세요.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 400, \"errorType\": \"Bad Request\", \"message\": \"필수 필드가 누락되었습니다.\"}"
                    )
            ))
    })
    @PostMapping
    public ResponseEntity<MatchResponseDTO> createMatch(@RequestBody @Valid MatchRegisterRequestDTO matchRegisterRequestDTO) {
        MatchResponseDTO createdMatch = matchService.saveMatch(matchRegisterRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    // 경기 수정
    @Operation(summary = "경기 수정", description = "등록된 경기를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경기가 성공적으로 수정되었습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MatchResponseDTO.class),
                    examples = @ExampleObject(
                            value = "{\"matchId\": 1, \"matchIntroduce\": \"Updated match description\", \"matchStatus\": \"IN_PROGRESS\", \"fieldLocation\": \"Busan\", \"matchDate\": \"2024-10-05\", \"matchStartTime\": \"15:00\", \"matchEndTime\": \"17:00\", \"myClub\": {\"clubId\": 1, \"clubName\": \"Seoul FC\"}, \"enemyClub\": {\"clubId\": 2, \"clubName\": \"Busan FC\"}}"
                    )
            )),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다. 입력 데이터를 확인해주세요.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 400, \"errorType\": \"Bad Request\", \"message\": \"필수 필드가 누락되었습니다.\"}"
                    )
            )),
            @ApiResponse(responseCode = "404", description = "등록된 경기가 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"경기를 찾을 수 없습니다. matchId=1\"}"
                    )
            ))
    })
    @PutMapping("/{matchId}")
    public ResponseEntity<MatchResponseDTO> updateMatch(@PathVariable Long matchId, @RequestBody MatchRegisterRequestDTO updateRequestDTO) {
        MatchResponseDTO updatedMatch = matchService.updateMatch(matchId, updateRequestDTO);
        return ResponseEntity.ok(updatedMatch);
    }

    // 경기 삭제
    @Operation(summary = "경기 삭제", description = "등록된 경기를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "경기가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "등록된 경기가 존재하지 않습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = "{\"status\": 404, \"errorType\": \"Not Found\", \"message\": \"경기를 찾을 수 없습니다. matchId=1\"}"
                    )
            ))
    })
    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long matchId) {
        matchService.removeMatch(matchId);
        return ResponseEntity.noContent().build();
    }
}
