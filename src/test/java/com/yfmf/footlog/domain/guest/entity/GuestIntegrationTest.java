package com.yfmf.footlog.domain.guest;

import com.yfmf.footlog.domain.guest.dto.CreateGuestDto;
import com.yfmf.footlog.domain.guest.entity.Guest;
import com.yfmf.footlog.domain.guest.repository.GuestRepository;
import com.yfmf.footlog.domain.guest.service.GuestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        CreateGuestDto createGuestDto = CreateGuestDto.builder()
                .name("Test Guest")
                .createdAt(LocalDateTime.now())
                .isAvailable(true)
                .build();

        // When
        Guest registeredGuest = guestService.registerGuest(createGuestDto);

        // Then
        assertThat(registeredGuest).isNotNull();
        assertThat(registeredGuest.getId()).isNotNull();
        assertThat(registeredGuest.getName()).isEqualTo("Test Guest");
        assertThat(registeredGuest.getIsAvailable()).isTrue();

        // Verify that the guest is actually saved in the database
        Guest foundGuest = guestRepository.findById(registeredGuest.getId()).orElse(null);
        assertThat(foundGuest).isNotNull();
        assertThat(foundGuest.getName()).isEqualTo("Test Guest");
        assertThat(foundGuest.getIsAvailable()).isTrue();
    }
}