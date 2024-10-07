package com.yfmf.footlog.domain.guest.entity;


import com.yfmf.footlog.domain.guest.dto.CreateGuestDto;
import com.yfmf.footlog.domain.guest.repository.GuestRepository;
import com.yfmf.footlog.domain.guest.service.GuestService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;


/*
 * Unit Test (with Mockito)
 *
 * 데이터베이스와의 연동 없이
 * 해당 클래스의 메소드의 필드(GuestRepository)를 Mocking하여
 * busuness logic을 테스트하는것. 실수로 인해 데이터베이스에 영향을 주지 않도록 하기 위함 !
 * */

class GuestServiceUnitTests {
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
                .available(true)
                .build();

        guestRepository.save(any(Guest.class));

    }



}
