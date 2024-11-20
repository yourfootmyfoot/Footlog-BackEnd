package com.yfmf.footlog.domain.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.Arrays;
import java.util.Collections;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class Stat {
    private static final int MIN_STAT = 0;
    private static final int MAX_STAT = 100;

    @NotNull(message = "체력 수치를 입력해주세요.")
    @Min(value = MIN_STAT, message = "체력은 " + MIN_STAT + " 이상이어야 합니다.")
    @Max(value = MAX_STAT, message = "체력은 " + MAX_STAT + " 이하여야 합니다.")
    private Integer stamina;  // 체력

    @NotNull(message = "수비 수치를 입력해주세요.")
    @Min(value = MIN_STAT, message = "수비는 " + MIN_STAT + " 이상이어야 합니다.")
    @Max(value = MAX_STAT, message = "수비는 " + MAX_STAT + " 이하여야 합니다.")
    private Integer defend;   // 수비

    @NotNull(message = "스피드 수치를 입력해주세요.")
    @Min(value = MIN_STAT, message = "스피드는 " + MIN_STAT + " 이상이어야 합니다.")
    @Max(value = MAX_STAT, message = "스피드는 " + MAX_STAT + " 이하여야 합니다.")
    private Integer speed;    // 스피드

    @NotNull(message = "패스 수치를 입력해주세요.")
    @Min(value = MIN_STAT, message = "패스는 " + MIN_STAT + " 이상이어야 합니다.")
    @Max(value = MAX_STAT, message = "패스는 " + MAX_STAT + " 이하여야 합니다.")
    private Integer pass;     // 패스

    @NotNull(message = "슛 수치를 입력해주세요.")
    @Min(value = MIN_STAT, message = "슛은 " + MIN_STAT + " 이상이어야 합니다.")
    @Max(value = MAX_STAT, message = "슛은 " + MAX_STAT + " 이하여야 합니다.")
    private Integer shoot;    // 슛

    @NotNull(message = "드리블 수치를 입력해주세요.")
    @Min(value = MIN_STAT, message = "드리블은 " + MIN_STAT + " 이상이어야 합니다.")
    @Max(value = MAX_STAT, message = "드리블은 " + MAX_STAT + " 이하여야 합니다.")
    private Integer dribble;  // 드리블

    @Builder
    public Stat(Integer stamina, Integer defend, Integer speed,
                Integer pass, Integer shoot, Integer dribble) {
        this.stamina = stamina;
        this.defend = defend;
        this.speed = speed;
        this.pass = pass;
        this.shoot = shoot;
        this.dribble = dribble;
    }

    // 평균 능력치 계산
    public double getAverageStat() {
        return (double) (stamina + defend + speed + pass + shoot + dribble) / 6;
    }

    // 최대 능력치 계산
    public Integer getMaxStat() {
        return Collections.max(Arrays.asList(stamina, defend, speed, pass, shoot, dribble));
    }

    // 최소 능력치 계산
    public Integer getMinStat() {
        return Collections.min(Arrays.asList(stamina, defend, speed, pass, shoot, dribble));
    }

    // 포지션별 능력치 계산 메서드 예시
    public double getStrikerStat() {
        return (shoot * 0.4 + speed * 0.3 + dribble * 0.3);
    }

    public double getDefenderStat() {
        return (defend * 0.4 + stamina * 0.3 + speed * 0.3);
    }

    public double getMidfielderStat() {
        return (pass * 0.4 + stamina * 0.3 + dribble * 0.3);

    }
}
