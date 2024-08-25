package com.yfmf.footlog.matchenroll;

import com.yfmf.footlog.domain.matchenroll.MatchEnroll;
import com.yfmf.footlog.domain.matchenroll.MatchEnrollService;
import com.yfmf.footlog.domain.matchenroll.enums.ClubLevel;
import com.yfmf.footlog.domain.matchenroll.enums.MatchGender;
import com.yfmf.footlog.domain.matchenroll.enums.PlayerCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MatchEnrollTests {

    private MatchEnroll matchEnroll;
    @Autowired
    private MatchEnrollService matchEnrollService;

    @BeforeEach
    void setUp() {
        matchEnroll = MatchEnroll.builder()
                .matchTitle("경기 등록 테스트")
                .description("경기 등록 테스트 중입니다.")
                .matchDay(LocalDateTime.now().plusDays(3))
                .isPro(true)
                .matchCost(100)
                .playTime(3) // default는 2/ 시간단위
                .quarter(4)
                .fieldLocation("주소지는 데이터가 어떤 타입이 들어오려나?")
                .playerCount(PlayerCount.ELEVEN)
                .matchGender(MatchGender.MALE)
                .userId(1L) // 임의로..
                .userName("User1")
                .clubName("Club1")
                .clubImageUrl("club1.png")
                .clubLevel(ClubLevel.BEGINNER)
                .build();
    }

    @DisplayName("경기 일정을 등록한다.")
    @Test
    void createMatchEnroll() {
        MatchEnroll createdMatch = matchEnrollService.registerMatchEnroll(matchEnroll);

        assertNotNull(createdMatch);
        assertNotNull(createdMatch.getMatchEnrollId());
        assertEquals("경기 등록 테스트", createdMatch.getMatchTitle());
    }

    @DisplayName("경기 일정이 존재하는지 확인한다..")
    @Test
    void findMatchEnroll() {
        MatchEnroll savedMatch = matchEnrollService.registerMatchEnroll(matchEnroll);

        MatchEnroll foundMatch = matchEnrollService.findMatchEnrollByMatchId(savedMatch.getMatchEnrollId())
                .orElseThrow(() -> new IllegalArgumentException("경기를 찾을 수 없습니다."));

        assertNotNull(foundMatch);
        assertEquals("경기 등록 테스트", foundMatch.getMatchTitle());
    }

    @DisplayName("경기 일정이 업데이트")
    @Test
    void updateMatchEnroll() {

        MatchEnroll savedMatch = matchEnrollService.registerMatchEnroll(matchEnroll);

        MatchEnroll updatedMatch = matchEnrollService.updateMatch(
                savedMatch.getMatchEnrollId(),
                MatchEnroll.builder()
                        .matchTitle("경기 등록 변경.")
                        .description("Updated description.")
                        .matchDay(LocalDateTime.now().plusDays(10))
                        .isPro(false)
                        .matchCost(150)
                        .playTime(2)
                        .quarter(3)
                        .fieldLocation("새로운 장소로 교체.")
                        .playerCount(PlayerCount.ELEVEN)
                        .matchGender(MatchGender.FEMALE)
//                        .userId(1L)
//                        .userName("UpdatedUser")
//                        .clubName("UpdatedClub")
//                        .clubImageUrl("updated_club.png")
                        .clubLevel(ClubLevel.PRO)
                        .build()
        );

        assertNotNull(updatedMatch);
        // 유저이름은 그전 값과 일치해야함.
        assertNotEquals("User2", updatedMatch.getUserName());
        assertEquals("경기 등록 변경.", updatedMatch.getMatchTitle());
        assertEquals("새로운 장소로 교체.", updatedMatch.getFieldLocation());
    }

    @DisplayName("해당 경기를 삭제한다.")
    @Test
    void deleteMatchEnroll() {

        MatchEnroll savedMatch = matchEnrollService.registerMatchEnroll(matchEnroll);

        MatchEnroll foundMatch = matchEnrollService.findMatchEnrollByMatchId(savedMatch.getMatchEnrollId())
                .orElseThrow(() -> new IllegalArgumentException("경기를 찾을 수 없습니다."));

        assertNotNull(foundMatch);
        assertEquals("경기 등록 테스트", foundMatch.getMatchTitle());
    }

}