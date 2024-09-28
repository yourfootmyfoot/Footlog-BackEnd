package com.yfmf.footlog.domain.member.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class Stat {

    private Integer stamina;

    private Integer defend;

    private Integer speed;

    private Integer pass;

    private Integer shoot;

    private Integer dribble;

    public Stat(Integer stamina, Integer defend, Integer speed, Integer pass, Integer shoot, Integer dribble) {

        validateStat(stamina, defend, speed, pass, shoot, dribble);
        this.stamina = stamina;
        this.defend = defend;
        this.speed = speed;
        this.pass = pass;
        this.shoot = shoot;
        this.dribble = dribble;
    }

    private void validateStamina(int stamina) {

        if (stamina < 0) {
            throw new IllegalArgumentException("체력 수치는 음수일 수 없습니다.");
        }
    }

    private void validateDefend(int defend) {

        if (defend < 0) {
            throw new IllegalArgumentException("수비 수치는 음수일 수 없습니다.");
        }
    }

    private void validateSpeed(int speed) {

        if (speed < 0) {
            throw new IllegalArgumentException("스피드 수치는 음수일 수 없습니다.");
        }
    }

    private void validatePass(int pass) {

        if (pass < 0) {
            throw new IllegalArgumentException("패스 수치는 음수일 수 없습니다.");
        }
    }

    private void validateShoot(int shoot) {

        if (shoot < 0) {
            throw new IllegalArgumentException("슛 수치는 음수일 수 없습니다.");
        }
    }

    private void validateDribble(int dribble) {

        if (dribble < 0) {
            throw new IllegalArgumentException("드리블 수치는 음수일 수 없습니다.");
        }
    }

    private void validateStat(Integer stamina, Integer defend, Integer speed, Integer pass, Integer shoot, Integer dribble) {
        validateStamina(stamina);
        validateDefend(defend);
        validateSpeed(speed);
        validatePass(pass);
        validateShoot(shoot);
        validateDribble(dribble);
    }
}
