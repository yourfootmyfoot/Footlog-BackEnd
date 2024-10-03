package com.yfmf.footlog.domain.admin.controller;


import com.yfmf.footlog.domain.admin.service.AdminService;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.service.ClubService;
import com.yfmf.footlog.domain.guest.service.GuestService;
import com.yfmf.footlog.domain.match.entity.Match;
import com.yfmf.footlog.domain.match.service.MatchService;
import com.yfmf.footlog.domain.member.dto.MemberResponseDTO;
import com.yfmf.footlog.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {

    private final MemberService memberService;

    private final ClubService clubService;

    private final MatchService matchService;

    private final GuestService guestService;

    @Autowired
    public AdminController(MemberService memberService, ClubService clubService, MatchService matchService, GuestService guestService) {
        this.memberService = memberService;
        this.clubService = clubService;
        this.matchService = matchService;
        this.guestService = guestService;
    }

    @GetMapping("/test")
    public String testMethod() {
        return "test success";
    }

    // user 조회
    @GetMapping("/member")
    public String memberList() {

        ArrayList<MemberResponseDTO> memberList = new ArrayList<>();
        return "user list success";
    }

    // match 조회
    @GetMapping("/match")
    public String matchList() {

        return "user list success";
    }

    // match result 조회
    @GetMapping("/match-result")
    public String matchResultList() {

        // List<Match> matches = matchService.getAllMatches();
        return "user list success";
    }

    // club 조회
    @GetMapping("/club")
    public String clubList() {

        List<Club> clubs = clubService.getAllClubs();
        return "user list success";
    }

    // mercenary-app 조회
    @GetMapping("/mercenary-app")
    public String mercenaryAppList() {

        return "user list success";
    }

    // mercenary-mec
    // user 조회
    @GetMapping("/mercenary-rec")
    public String mercenaryRecList() {

        return "user list success";
    }

}

