package com.yfmf.footlog.domain.club.controller;

import com.yfmf.footlog.domain.club.dto.ClubRegistRequestDTO;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    // 구단 등록
    @PostMapping
    public ResponseEntity<String> createClub(@RequestBody ClubRegistRequestDTO clubInfo) {
        clubService.registClub(clubInfo);
        return ResponseEntity.ok("구단이 성공적으로 등록되었습니다.");
    }

    // 모든 구단 조회
    @GetMapping
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }

    // 구단주 아이도로 구단 조회
    @GetMapping("/owner/{userId}")
    public ResponseEntity<List<Club>> getClubsByUserId(@PathVariable Long userId) {
        List<Club> clubs = clubService.getClubsByUserId(userId);
        if (clubs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clubs);
    }

    // 구단 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<String> updateClub(@PathVariable Long id, @RequestBody ClubRegistRequestDTO clubInfo) {
        try {
            clubService.updateClub(id, clubInfo);
            return ResponseEntity.ok("구단이 성공적으로 업데이트되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 구단 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClub(@PathVariable Long id) {
        try {
            clubService.deleteClub(id);
            return ResponseEntity.ok("구단이 성공적으로 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}