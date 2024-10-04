package com.yfmf.footlog.domain.match.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchSchedule {

    // 경기 날짜
    @Column(nullable = false)
    private LocalDate matchDate;

    // 경기 시작 시간
    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime matchStartTime;

    // 경기 종료 시간 - 경기 시작 시간을 설정하면 뒤에 수정 가능
    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime matchEndTime;

    // 경기 시간
    @Column(nullable = false)
    private long matchTime;

    public MatchSchedule(LocalDate matchDate, LocalTime matchStartTime, LocalTime matchEndTime) {
        // 유효성 검사
        validateMatch(matchStartTime, matchEndTime);

        this.matchDate = matchDate;
        this.matchStartTime = matchStartTime;
        this.matchEndTime = matchEndTime;
        this.matchTime = calculateTime(matchStartTime, matchEndTime);
    }

    // 경기 시간 계산
    private long calculateTime(LocalTime matchStartTime, LocalTime matchEndTime) {
        return ChronoUnit.SECONDS.between(matchStartTime, matchEndTime);
    }

    /* 예외 처리 */

    // 경기 시작 시간 null 금지
    private void validateMatchStartTime(LocalTime matchStartTime) {
        if (matchStartTime == null) {
            throw new IllegalArgumentException("경기 시작 시간을 입력해 주세요");
        }
    }

    // 경기 종료 시간 null 금지
    private void validateMatchEndTime(LocalTime matchEndTime) {
        if (matchEndTime == null) {
            throw new IllegalArgumentException("경기 종료 시간을 입력해 주세요");
        }
    }

    // 경기 종료 시간이 시작 시간보다 뒤인지 확인
    private void validateMatchTimeOrder(LocalTime matchStartTime, LocalTime matchEndTime) {
        if (matchEndTime.isBefore(matchStartTime)) {
            throw new IllegalArgumentException("경기 종료 시간은 경기 시작 시간보다 앞에 있을 수 없습니다.");
        }
    }

    // 경기 시간이 2시간 미만인지 확인
    private void validateMinimumTwoHours(LocalTime matchStartTime, LocalTime matchEndTime) {
        if (ChronoUnit.MINUTES.between(matchStartTime, matchEndTime) < 120) {
            throw new IllegalArgumentException("경기 최소 시간은 2시간입니다.");
        }
    }

    // 경기 시간이 4시간 초과인지 확인
    private void validateMaximumFourHours(LocalTime matchStartTime, LocalTime matchEndTime) {
        if (ChronoUnit.HOURS.between(matchStartTime, matchEndTime) > 4) {
            throw new IllegalArgumentException("경기 최대 시간은 4시간입니다.");
        }
    }

    // 전체 유효성 검사
    private void validateMatch(LocalTime matchStartTime, LocalTime matchEndTime) {
        validateMatchStartTime(matchStartTime);
        validateMatchEndTime(matchEndTime);
        validateMatchTimeOrder(matchStartTime, matchEndTime);
        validateMinimumTwoHours(matchStartTime, matchEndTime);
        validateMaximumFourHours(matchStartTime, matchEndTime);
    }
}
