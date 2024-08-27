package com.yfmf.footlog.match.command.domain.model;

import com.yfmf.footlog.domain.club.Club;
import com.yfmf.footlog.match.command.domain.model.dto.LoadMatchResponseDTO;
import com.yfmf.footlog.match.command.domain.model.dto.MatchRegistRequestDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static com.yfmf.footlog.match.command.domain.model.enums.ClubLevel.*;
import static com.yfmf.footlog.match.command.domain.model.enums.MatchGender.*;
import static com.yfmf.footlog.match.command.domain.model.enums.MatchPlayerQuantity.*;
import static com.yfmf.footlog.match.command.domain.model.enums.MatchStatus.*;
import static com.yfmf.footlog.match.command.domain.model.enums.QuarterQuantity.*;

@Transactional
@SpringBootTest
public class MatchTests {

    @Autowired
    private MatchService matchService;


    @DisplayName("경기 전체 목록 조회 테스트")
    @Test
    void testLoadAllMatch() {

        Assertions.assertDoesNotThrow(
                () -> {
                    List<LoadMatchResponseDTO> matches = matchService.loadAllMatches();
                    matches.forEach(System.out::println);
                }
        );
    }


    private static Stream<Arguments> newMatch() {
        return Stream.of(
                Arguments.of(
                        new MatchRegistRequestDTO(
                                LocalDateTime.now(),
                                1L,
                                2L,
                                new Club(),
                                new Club(),
                                "매치 사진",
                                "매치 설명",
                                new MatchSchedule(LocalDate.now(), LocalTime.now(), LocalTime.now()),
                                PLAYER_QUANTITY_ELEVEN,
                                QUARTER_QUANTITY_FOUR,
                                "종합운동장",
                                10000,
                                new Pro(),
                                LEVEL_MEDIUM,
                                GENDER_MALE,
                                MATCH_STATUS_IN_PROGRESS
                        )
                ));
    }

    @Transactional
    @ParameterizedTest
    @MethodSource("newMatch")
    @DisplayName("경기 등록 테스트")
    void testRegistMatch(MatchRegistRequestDTO newMatch) {
        Assertions.assertDoesNotThrow(
                () -> matchService.registMatch(newMatch)
        );

        matchService.loadAllMatches().forEach(System.out::println);
    }


    @CsvSource({"1, 수정할 경기 이름 1", "2, 수정할 경기 이름 2"})
    @ParameterizedTest
    @DisplayName("경기 ID로 경기 수정 테스트")
    void testFindMatchById(Long matchId, String matchIntroduce) {

        Assertions.assertDoesNotThrow(
                () -> matchService.modifyMatch(matchId, matchIntroduce)
        );

        matchService.loadAllMatches().forEach(System.out::println);
    }


    @Transactional
    @ParameterizedTest
    @ValueSource(longs = {1})
    @DisplayName("경기 ID로 경기 삭제")
    void testRemoveMatchById(Long matchId) {
        Assertions.assertDoesNotThrow(
                () -> matchService.removeMatch(matchId)
        );

        matchService.loadAllMatches().forEach(System.out::println);
    }
}

