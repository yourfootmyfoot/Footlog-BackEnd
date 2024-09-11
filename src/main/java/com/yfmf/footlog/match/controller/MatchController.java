package com.yfmf.footlog.match.controller;

import com.yfmf.footlog.match.dto.LoadMatchResponseDTO;
import com.yfmf.footlog.match.entity.MatchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {

    private MatchService matchService;

    public MatchController() {
    }

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // 매치 id로 매치 정보 반환
    @GetMapping("/detail/{matchId}")
    public LoadMatchResponseDTO findAllMatches(@PathVariable Long matchId) {
        
        return matchService.findMatchByMatchId(matchId);
    }


}
