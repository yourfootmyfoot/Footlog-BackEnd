package com.yfmf.footlog.domain.match.controller;

import com.yfmf.footlog.domain.match.dto.LoadMatchResponseDTO;
import com.yfmf.footlog.domain.match.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/match")
public class MatchController {

    private MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // 매치 id로 매치 정보 반환
    @GetMapping("/detail/{matchId}")
    public LoadMatchResponseDTO findAllMatches(@PathVariable Long matchId) {
        
        return matchService.findMatchByMatchId(matchId);
    }

    @Operation(summary = "테스트테스트", description = "새로운 구단을 등록합니다.")
    @GetMapping("/matches/test")
    public String testMethod() {
        return "test success";
    }

}
