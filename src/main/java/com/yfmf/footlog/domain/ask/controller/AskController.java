package com.yfmf.footlog.domain.ask.controller;

import com.yfmf.footlog.domain.ask.dto.AskCreateRequestDto;
import com.yfmf.footlog.domain.ask.dto.AskCreateResponseDto;
import com.yfmf.footlog.domain.ask.dto.AskResponseDto;
import com.yfmf.footlog.domain.ask.service.AskService;
import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/ask")
@Tag(name = "Ask", description = "1대1 문의 API")
public class AskController {

    private static final Logger log = LoggerFactory.getLogger(AskController.class);
    private final AskService askService;

    @PostMapping
    @Operation(summary = "1대1 문의 등록", description = "새로운 1대1 문의를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "1대1 문의 등록 성공")
    })
    public ResponseEntity<AskCreateResponseDto> createAsk(@Valid @RequestBody AskCreateRequestDto requestDto,
                                                          @AuthenticationPrincipal LoginedInfo logined) {

        requestDto.setUserId(logined.getUserId());
        AskCreateResponseDto responseDto = askService.createAsk(requestDto);

        log.info("[AskController] 문의 등록 완료: 문의 등록 유저 ID={}, 문의 ID={}", logined.getUserId(), responseDto.getAskId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    @Operation(summary = "1대1 문의 전체 조회", description = "1대1 문의 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "1대1 문의 목록 조회 성공")
    })
    public ResponseEntity<List<AskResponseDto>> getAllAsk() {

        log.info("[AskController] 모든 문의 조회 요청");
        List<AskResponseDto> askList = askService.getAllAsks();
        return ResponseEntity.status(HttpStatus.OK).body(askList);
    }
    
    // 유저 아이디로 조회
    // 완료/미완료 정리도 해야겠다
    // 답변 기능
}
