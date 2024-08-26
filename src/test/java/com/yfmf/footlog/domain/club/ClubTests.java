package com.yfmf.footlog.domain.club;

import com.yfmf.footlog.users.UserRole;
import com.yfmf.footlog.users.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
public class ClubTests {

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubService clubService;

    private static Stream<Arguments> createClub() {
        User user = new User(
                null,          // userId (자동 생성)
                123456789L,    // kakaoId
                "clubOwner",   // userName
                LocalDate.of(1990, 1, 1), // birth
                null,          // mainFoot (null 값으로 대체)
                null,          // area (null 값으로 대체)
                null,          // position (null 값으로 대체)
                "안녕하세요", // introduction
                false,         // isPro
                180.0,         // height
                75.0,          // weight
                null,          // profileImageUrl (null 값으로 대체)
                "010-1234-5678", // phoneNumber
                UserRole.ROLE_USER, // role
                null,          // stat (null 값으로 대체)
                null           // record (null 값으로 대체)
        );

        return Stream.of(
                Arguments.of(
                        user,
                        "metaverse",
                        "안녕하세요",
                        "kknd",
                        LocalDateTime.now(),
                        List.of(PeakDays.월, PeakDays.수),
                        PeakHours.낮
                )
        );
    }

    @DisplayName("구단 등록 테스트")
    @ParameterizedTest
    @MethodSource("createClub")
    void testCreateClub(User clubOwner, String clubName,
                        String clubIntroduction, String clubCode, LocalDateTime enrollDate,
                        List<PeakDays> peakDays, PeakHours peakHours) {

        ClubRegistRequestDTO clubInfo = new ClubRegistRequestDTO(
                clubOwner,
                clubName,
                clubIntroduction,
                clubCode,
                enrollDate,
                peakDays,
                peakHours
                );

        Assertions.assertDoesNotThrow(
                () -> clubService.registClub(clubInfo)
        );
    }
}
