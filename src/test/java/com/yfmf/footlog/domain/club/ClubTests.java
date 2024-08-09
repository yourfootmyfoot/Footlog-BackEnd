package com.yfmf.footlog.domain.club;

import com.example.subjectjpa.user.User;
import com.example.subjectjpa.user.UserRole;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        User user = new User("clubOwner", LocalDateTime.now(), UserRole.ROLE_USER);
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
