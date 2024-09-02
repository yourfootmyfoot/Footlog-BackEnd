package com.yfmf.footlog.guest.entity;


import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;

import com.yfmf.footlog.guest.dto.CreateGuestDto;
import com.yfmf.footlog.guest.dto.UpdateGuestDto;
import com.yfmf.footlog.guest.repository.GuestRepository;
import com.yfmf.footlog.guest.service.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/*
 * Unit Test (with Mockito)
 *
 * 데이터베이스와의 연동 없이
 * 해당 클래스의 메소드의 필드(GuestRepository)를 Mocking하여
 * busuness logic을 테스트하는것. 실수로 인해 데이터베이스에 영향을 주지 않도록 하기 위함 !
 * */

class GuestServiceTests {
    // GuestService를 테스트하기 위해 GuestRepository를 Mocking한다.
    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    // @Mock으로 Mocking된 GuestRepository를 찾아 GuestService에 주입한다.
    // 테스트 실행 전 MockitoAnnotations.openMocks(this)를 호출하여 Mock 객체를 초기화한다.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerGuestShouldReturnSavedGuest() {
        // Given
        CreateGuestDto createGuestDto = CreateGuestDto.builder()
                .name("junsu")
                .createdAt(LocalDateTime.now())
                .mainFoot(MainFoot.오른발)
                .position(Position.ST)
                .isAvailable(true)
                .build();

        guestRepository.save(any(Guest.class));

    }
    public Guest updateGuest(UpdateGuestDto updateGuestDto) {
        Optional<Guest> existingGuestOptional = guestRepository.findById(updateGuestDto.getId());

        if (existingGuestOptional.isPresent()) {
            Guest existingGuest = existingGuestOptional.get();

            // 기존 Guest의 속성을 유지하며, 업데이트할 속성만 변경
            Guest updatedGuest = Guest.builder()
                    .id(existingGuest.getId())
                    .name(updateGuestDto.getName() != null ? updateGuestDto.getName() : existingGuest.getName())
                    .createdAt(existingGuest.getCreatedAt()) // 생성일자는 변경하지 않음
                    .mainFoot(updateGuestDto.getMainFoot() != null ? updateGuestDto.getMainFoot() : existingGuest.getMainFoot())
                    .position(updateGuestDto.getPosition() != null ? updateGuestDto.getPosition() : existingGuest.getPosition())
                    .isAvailable(updateGuestDto.getIsAvailable())
                    .build();

            return guestRepository.save(updatedGuest);
        } else {
            throw new IllegalArgumentException("Guest not found");
        }
    }


}
