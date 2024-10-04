package com.yfmf.footlog.domain.guest.controller;

import com.yfmf.footlog.domain.guest.dto.GuestSaveRequestDto;
import com.yfmf.footlog.domain.guest.entity.Guest;
import com.yfmf.footlog.domain.guest.service.GuestService;
//import com.yfmf.footlog.domain.guest.dto.GuestSaveRequestDto;
import com.yfmf.footlog.domain.guest.dto.GuestUpdateRequestDto;
import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest", description = "용병 API")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @Operation(summary = "용병 등록", description = "새로운 용병을 등록합니다.")
    @ApiResponse(responseCode = "201", description = "용병 등록 성공")
    public ResponseEntity<Guest> registerGuest(@AuthenticationPrincipal LoginedInfo loginedInfo,
                                               @Valid @RequestBody GuestSaveRequestDto request) {
        Guest guest = guestService.registerGuest(loginedInfo.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(guest);
    }

    @GetMapping
    @Operation(summary = "모든 용병 조회", description = "모든 용병을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "용병 목록 조회 성공")
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @GetMapping("/available")
    @Operation(summary = "사용 가능한 용병 조회", description = "현재 사용 가능한 모든 용병을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "사용 가능한 용병 목록 조회 성공")
    public ResponseEntity<List<Guest>> getAvailableGuests(@RequestParam LocalDateTime date) {
        return ResponseEntity.ok(guestService.findAvailableGuests(date));
    }

    @GetMapping("/{guestId}")
    @Operation(summary = "용병 조회", description = "특정 ID의 용병을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "용병 조회 성공")
    @ApiResponse(responseCode = "404", description = "용병을 찾을 수 없음")
    public ResponseEntity<Guest> getGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @PutMapping("/{guestId}")
    @Operation(summary = "용병 정보 수정", description = "용병의 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "용병 정보 수정 성공")
    @ApiResponse(responseCode = "404", description = "용병을 찾을 수 없음")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long guestId,
                                             @Valid @RequestBody GuestUpdateRequestDto request) {
        Guest updatedGuest = guestService.updateGuest(guestId, request);
        return ResponseEntity.ok(updatedGuest);
    }

    @PutMapping("/{guestId}/availability")
    @Operation(summary = "용병 가용성 업데이트", description = "용병의 가용성 상태를 업데이트합니다.")
    @ApiResponse(responseCode = "200", description = "용병 가용성 업데이트 성공")
    @ApiResponse(responseCode = "404", description = "용병을 찾을 수 없음")
    public ResponseEntity<Guest> updateGuestAvailability(@PathVariable Long guestId,
                                                         @RequestParam boolean available) {
        return ResponseEntity.ok(guestService.updateGuestAvailability(guestId, available));
    }

    @DeleteMapping("/{guestId}")
    @Operation(summary = "용병 삭제", description = "특정 ID의 용병을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "용병 삭제 성공")
    @ApiResponse(responseCode = "404", description = "용병을 찾을 수 없음")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-position")
    @Operation(summary = "포지션별 용병 조회", description = "특정 선호 포지션의 사용 가능한 용병을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "포지션별 용병 조회 성공")
    public ResponseEntity<List<Guest>> getGuestsByPosition(@RequestParam String preferredPosition) {
        return ResponseEntity.ok(guestService.findGuestsByLocation(preferredPosition));
    }
}
