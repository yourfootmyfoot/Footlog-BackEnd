package com.yfmf.footlog.domain.club.controller;

import com.yfmf.footlog.domain.club.dto.ClubRegistRequestDTO;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    // 클럽 등록
    @PostMapping
    public ResponseEntity<String> createClub(@RequestBody ClubRegistRequestDTO clubInfo) {
        clubService.registClub(clubInfo);
        return ResponseEntity.ok("Club registered successfully");
    }

    // 모든 클럽 조회
    @GetMapping
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }

    // 특정 클럽 조회
    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        Optional<Club> club = clubService.getClubById(id);
        return club.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 클럽 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<String> updateClub(@PathVariable Long id, @RequestBody ClubRegistRequestDTO clubInfo) {
        try {
            clubService.updateClub(id, clubInfo);
            return ResponseEntity.ok("Club updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 클럽 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClub(@PathVariable Long id) {
        try {
            clubService.deleteClub(id);
            return ResponseEntity.ok("Club deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}