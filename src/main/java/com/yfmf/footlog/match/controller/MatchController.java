package com.yfmf.footlog.match.controller;

import com.yfmf.footlog.match.dto.LoadMatchResponseDTO;
import com.yfmf.footlog.match.entity.MatchService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MatchController {

    private MatchService matchService;

    public MatchController() {
    }

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    public void findAllMatches() {

        List<LoadMatchResponseDTO> matchList = matchService.findAllMatches();
        matchList.forEach(System.out::println);
    }

}
