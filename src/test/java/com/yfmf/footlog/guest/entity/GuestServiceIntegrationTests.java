package com.yfmf.footlog.guest.entity;

import com.yfmf.footlog.domain.user.enums.MainFoot;
import com.yfmf.footlog.domain.user.enums.Position;
import com.yfmf.footlog.guest.repository.GuestRepository;
import com.yfmf.footlog.guest.service.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestServiceIntegrationTests {

    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestRepository guestRepository;

    @BeforeEach
    void setUp() {
        guestRepository.deleteAll(); // 테스트 전 데이터베이스를 초기화합니다.
    }

    @Test
    @Transactional
    void registerGuestShouldSaveAndReturnGuest() {
        // Given
        Guest guest = new Guest("junsu", LocalDateTime.now(), MainFoot.오른발, Position.ST, true);

        // When
        Guest savedGuest = guestService.registGuest(guest);

        // Then
        assertNotNull(savedGuest);
        assertEquals("junsu", savedGuest.getName());
        assertEquals(MainFoot.오른발, savedGuest.getMainFoot());
        assertEquals(Position.ST, savedGuest.getPosition());
        assertTrue(guestRepository.existsById(savedGuest.getId()));
    }

    @Test
    @Transactional
    void updateGuestShouldUpdateAndReturnGuest() {
        // Given
        Guest guest = new Guest("junsu", LocalDateTime.now(), MainFoot.오른발, Position.ST, true);
        Guest savedGuest = guestService.registGuest(guest);

        // When
        Guest updatedGuest = new Guest("junsu", LocalDateTime.now(), MainFoot.왼발, Position.LW, false);
        Guest result = guestService.updateGuest(savedGuest.getId(), updatedGuest);

        // Then
        assertNotNull(result);
        assertEquals(MainFoot.왼발, result.getMainFoot());
        assertEquals(Position.LW, result.getPosition());
    }

    @Test
    @Rollback(false)  // 이 메서드에서만 롤백을 방지
    @Transactional
    void deleteGuestShouldRemoveGuest() {
        // Given
        Guest guest = new Guest("junsu", LocalDateTime.now(), MainFoot.오른발, Position.ST, true);
        Guest savedGuest = guestService.registGuest(guest);

        // When
        guestService.deleteGuest(savedGuest.getId());

        // Then
        assertFalse(guestRepository.existsById(savedGuest.getId()));
    }


}
