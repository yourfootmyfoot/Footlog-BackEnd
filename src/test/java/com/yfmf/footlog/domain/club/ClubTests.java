/*
package com.yfmf.footlog.domain.club;

import com.yfmf.footlog.domain.club.enums.PeakDays;
import com.yfmf.footlog.domain.club.enums.PeakHours;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import com.yfmf.footlog.domain.club.service.ClubService;
import com.yfmf.footlog.domain.user.enums.UserRole;
import com.yfmf.footlog.domain.users.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.params.provider.Arguments;
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


}
*/
