package com.yfmf.footlog.users;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RecordTest {

    private static Stream<Arguments> getRecord() {
        return Stream.of(
                Arguments.of(
                        1,
                        2,
                        3,
                        4
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getRecord")
    void createRecordTest(int totalMatch, int totalScore, int totalAssist, int totalMom) {

        Record record = new Record(totalMatch, totalScore, totalAssist, totalMom);

        assertThat(record.getTotalMatch()).isEqualTo(totalMatch);
        assertThat(record.getTotalScore()).isEqualTo(totalScore);
        assertThat(record.getTotalAssist()).isEqualTo(totalAssist);
        assertThat(record.getTotalMom()).isEqualTo(totalMom);
    }

    private static Stream<Arguments> negativeTotalMatch() {
        return Stream.of(
                Arguments.of(
                        -1,
                        2,
                        3,
                        4
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeTotalMatch")
    void negativeTotalMatchTest(int totalMatch, int totalScore, int totalAssist, int totalMom) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Record(totalMatch, totalScore, totalAssist, totalMom));

        assertThat(exception.getMessage()).isEqualTo("경기 수는 음수일 수 없습니다.");
    }

    private static Stream<Arguments> negativeTotalScore() {
        return Stream.of(
                Arguments.of(
                        1,
                        -2,
                        3,
                        4
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeTotalScore")
    void negativeTotalScoreTest(int totalMatch, int totalScore, int totalAssist, int totalMom) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Record(totalMatch, totalScore, totalAssist, totalMom));

        assertThat(exception.getMessage()).isEqualTo("총 득점은 음수일 수 없습니다.");
    }


    private static Stream<Arguments> negativeTotalAssist() {
        return Stream.of(
                Arguments.of(
                        1,
                        2,
                        -3,
                        4
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeTotalAssist")
    void negativeTotalAssistTest(int totalMatch, int totalScore, int totalAssist, int totalMom) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Record(totalMatch, totalScore, totalAssist, totalMom));

        assertThat(exception.getMessage()).isEqualTo("총 어시스트는 음수일 수 없습니다.");
    }

    private static Stream<Arguments> negativeTotalMom() {
        return Stream.of(
                Arguments.of(
                        1,
                        2,
                        3,
                        -4
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeTotalMom")
    void negativeTotalMomTest(int totalMatch, int totalScore, int totalAssist, int totalMom) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Record(totalMatch, totalScore, totalAssist, totalMom));

        assertThat(exception.getMessage()).isEqualTo("총 Mom은 음수일 수 없습니다.");
    }
}