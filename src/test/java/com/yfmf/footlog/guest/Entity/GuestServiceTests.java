package com.yfmf.footlog.guest.Entity;



import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;

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
* Unit Test
*
* 데이터베이스와의 연동 없이
* 해당 클래스의 메소드의 필드(GuestRepository)를 Mocking하여
* busuness logic을 테스트하는것. 실수로 인해 데이터베이스에 영향을 주지 않도록 하기 위함 !!!
*
*
* */

class GuestServiceTests {
    // GuestService를 테스트하기 위해 GuestRepository를 Mocking한다.
    @Mock
    private GuestRepository guestRepository;
    // @Mock으로 Mocking된 GuestRepository를 GuestService에 주입한다.
    @InjectMocks
    private GuestService guestService;

    // 테스트 실행 전 MockitoAnnotations.openMocks(this)를 호출하여 Mock 객체를 초기화한다.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // registerGuest 메소드를 테스트한다.
    @Test
    void registerGuestShouldReturnSavedGuest() {
        // Given
        Guest guest = new Guest("junsu", LocalDateTime.now(), MainFoot.오른발, Position.ST, true);

        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        // When
        Guest savedGuest = guestService.registGuest(guest);

        // Then
        assertNotNull(savedGuest);
        assertEquals("junsu", savedGuest.getName());
        assertEquals(MainFoot.오른발, savedGuest.getMainFoot());
        assertEquals(Position.ST, savedGuest.getPosition());
        verify(guestRepository, times(1)).save(guest);
    }

    @Test
    void updateGuestShouldUpdateAndReturnGuest() {
        // Given
        long guestId = 1L;
        Guest existingGuest = new Guest("junsu", LocalDateTime.now(), MainFoot.오른발, Position.ST, true);

        Guest updatedGuest = new Guest("junsu", LocalDateTime.now(), MainFoot.왼발, Position.LW, false);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(existingGuest));
        when(guestRepository.save(any(Guest.class))).thenReturn(existingGuest);

        // When
        Guest result = guestService.updateGuest(guestId, updatedGuest);

        // Then
        assertNotNull(result);
        assertEquals("junsu", result.getName());
        assertEquals(MainFoot.왼발, result.getMainFoot());
        assertEquals(Position.LW, result.getPosition());
        verify(guestRepository, times(1)).findById(guestId);
        verify(guestRepository, times(1)).save(existingGuest);
    }

    @Test
    void deleteGuestShouldDeleteGuest() {
        // Given
        long guestId = 1L;
        when(guestRepository.existsById(guestId)).thenReturn(true);

        // When
        guestService.deleteGuest(guestId);

        // Then
        verify(guestRepository, times(1)).deleteById(guestId);
    }

    @Test
    void deleteGuestShouldThrowExceptionIfGuestNotFound() {
        // Given
        long guestId = 1L;
        when(guestRepository.existsById(guestId)).thenReturn(false);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> guestService.deleteGuest(guestId));
    }
}
