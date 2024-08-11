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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



public class GuestTests {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    //is unit test? or integration test?
    //unit test
    @Test
    void registerGuestShouldReturnSavedGuest() {
        // Given
        Guest guest = new Guest("junsu", LocalDateTime.now(), MainFoot.오른발, Position.ST, true);

        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        // When
        Guest savedGuest = guestService.registerGuest(guest);

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
