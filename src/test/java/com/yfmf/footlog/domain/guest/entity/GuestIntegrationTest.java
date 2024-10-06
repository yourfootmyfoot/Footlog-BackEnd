package com.yfmf.footlog.domain.guest.entity;

import com.yfmf.footlog.domain.guest.dto.GuestSaveRequestDto;
import com.yfmf.footlog.domain.guest.repository.GuestRepository;
import com.yfmf.footlog.domain.guest.service.GuestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class GuestIntegrationTest {

    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    public void testRegisterGuest() {
        // Given
        GuestSaveRequestDto requestDto = GuestSaveRequestDto.builder()
                .memberId(1L)
                .location("Test Location")
                .age(20)
                .scheduleDate(LocalDateTime.now())
                .scheduleStartTime(LocalTime.now())
                .scheduleEndTime(LocalTime.now())
                .specialRequests("Test Request")
                .available(true)
                .build();

        // When
        Guest registeredGuest = guestService.registerGuest(requestDto.getMemberId() ,requestDto);

        // Then
        assertThat(registeredGuest).isNotNull();
        assertThat(registeredGuest.getId()).isNotNull();
        assertThat(registeredGuest.getLocation()).isEqualTo("Test Location");
        assertThat(registeredGuest.isAvailable()).isTrue();

        // Verify that the guest is actually saved in the database
        Guest foundGuest = guestRepository.findById(registeredGuest.getId()).orElse(null);
        assertThat(foundGuest).isNotNull();
        assertThat(foundGuest.getLocation()).isEqualTo("Test Location");
        assertThat(foundGuest.isAvailable()).isTrue();
    }
}