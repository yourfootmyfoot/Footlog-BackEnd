package com.yfmf.footlog.users;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StatTest {

    private static Stream<Arguments> getStat() {
        return Stream.of(
                Arguments.of(
                        0,
                        20,
                        40,
                        60,
                        80,
                        100
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getStat")
    void createStatTest(int stamina, int defend, int speed, int pass, int shoot, int dribble) {

        Stat stat = new Stat(
                stamina,
                defend,
                speed,
                pass,
                shoot,
                dribble
        );

        assertThat(stat.getStamina()).isEqualTo(stamina);
        assertThat(stat.getDefend()).isEqualTo(defend);
        assertThat(stat.getSpeed()).isEqualTo(speed);
        assertThat(stat.getPass()).isEqualTo(pass);
        assertThat(stat.getShoot()).isEqualTo(shoot);
        assertThat(stat.getDribble()).isEqualTo(dribble);
    }

    private static Stream<Arguments> negativeStamina() {
        return Stream.of(
                Arguments.of(
                        -20,
                        20,
                        40,
                        60,
                        80,
                        100
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeStamina")
    void negativeStaminaTest(int stamina, int defend, int speed, int pass, int shoot, int dribble) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Stat(
                        stamina,
                        defend,
                        speed,
                        pass,
                        shoot,
                        dribble
                )
        );

        assertThat(exception.getMessage()).isEqualTo("체력 수치는 음수일 수 없습니다.");
    }

    private static Stream<Arguments> negativeDefend() {
        return Stream.of(
                Arguments.of(
                        20,
                        -20,
                        40,
                        60,
                        80,
                        100
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeDefend")
    void negativeDefendTest(int stamina, int defend, int speed, int pass, int shoot, int dribble) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Stat(
                        stamina,
                        defend,
                        speed,
                        pass,
                        shoot,
                        dribble
                )
        );

        assertThat(exception.getMessage()).isEqualTo("수비 수치는 음수일 수 없습니다.");
    }

    private static Stream<Arguments> negativeSpeed() {
        return Stream.of(
                Arguments.of(
                        0,
                        20,
                        -40,
                        60,
                        80,
                        100
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeSpeed")
    void negativeSpeedTest(int stamina, int defend, int speed, int pass, int shoot, int dribble) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Stat(
                        stamina,
                        defend,
                        speed,
                        pass,
                        shoot,
                        dribble
                )
        );

        assertThat(exception.getMessage()).isEqualTo("스피드 수치는 음수일 수 없습니다.");
    }

    private static Stream<Arguments> negativePass() {
        return Stream.of(
                Arguments.of(
                        0,
                        20,
                        40,
                        -60,
                        80,
                        100
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativePass")
    void negativePassTest(int stamina, int defend, int speed, int pass, int shoot, int dribble) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Stat(
                        stamina,
                        defend,
                        speed,
                        pass,
                        shoot,
                        dribble
                )
        );

        assertThat(exception.getMessage()).isEqualTo("패스 수치는 음수일 수 없습니다.");
    }

    private static Stream<Arguments> negativeShoot() {
        return Stream.of(
                Arguments.of(
                        0,
                        20,
                        40,
                        60,
                        -80,
                        100
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeShoot")
    void negativeShootTest(int stamina, int defend, int speed, int pass, int shoot, int dribble) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Stat(
                        stamina,
                        defend,
                        speed,
                        pass,
                        shoot,
                        dribble
                )
        );

        assertThat(exception.getMessage()).isEqualTo("슛 수치는 음수일 수 없습니다.");
    }

    private static Stream<Arguments> negativeDribble() {
        return Stream.of(
                Arguments.of(
                        0,
                        20,
                        40,
                        60,
                        80,
                        -100
                )
        );
    }

    @ParameterizedTest
    @MethodSource("negativeDribble")
    void negativeDribbleTest(int stamina, int defend, int speed, int pass, int shoot, int dribble) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Stat(
                        stamina,
                        defend,
                        speed,
                        pass,
                        shoot,
                        dribble
                )
        );

        assertThat(exception.getMessage()).isEqualTo("드리블 수치는 음수일 수 없습니다.");
    }
}