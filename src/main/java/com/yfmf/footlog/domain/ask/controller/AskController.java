package com.yfmf.footlog.domain.ask.controller;

import com.yfmf.footlog.domain.ask.dto.AskCreateRequestDto;
import com.yfmf.footlog.domain.ask.dto.AskCreateResponseDto;
import com.yfmf.footlog.domain.ask.entity.Ask;
import com.yfmf.footlog.domain.ask.service.AskService;
import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/ask")
@Tag(name = "Ask", description = "1대1 문의 API")
public class AskController {

    private static final Logger log = LoggerFactory.getLogger(AskController.class);
    private final AskService askService;

    @PostMapping
    @Operation(summary = "1대1 문의 등록", description = "새로운 1대1 문의를 등록합니다.")
    public ResponseEntity<AskCreateResponseDto> createAsk(@Valid @RequestBody AskCreateRequestDto requestDto,
                                                          @AuthenticationPrincipal LoginedInfo logined) {

        requestDto.setUserId(logined.getUserId());
        AskCreateResponseDto responseDto = askService.createAsk(requestDto);

        log.info("[AskController] 문의 등록 완료: 문의 등록 유저 ID={}, 문의 ID={}", logined.getUserId(), responseDto.getAskId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
