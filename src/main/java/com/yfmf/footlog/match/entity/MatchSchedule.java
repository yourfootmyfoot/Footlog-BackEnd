package com.yfmf.footlog.match.entity;

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

    // 경기날짜
    @Column(nullable = false)
    private LocalDate matchDate;

    // 경기 시작 시간
    @Column(nullable = false)
    private LocalTime matchStartTime;

    // 경기 종료 시간 - 경기 시작 시간을 설정하면 +2시간을 해주고 뒤에 수정 가능
    @Column(nullable = false)
    private LocalTime matchEndTime;

    // 경기 시간
    @Column(nullable = false)
    private long matchTime;

    public MatchSchedule(LocalDate matchDate, LocalTime matchStartTime, LocalTime matchEndTime) {

        // 에러 검사
        validMatch(matchStartTime, matchEndTime);

        this.matchDate = matchDate;
        this.matchStartTime = matchStartTime;
        this.matchEndTime = matchEndTime;
        this.matchTime = calculateTime(matchStartTime, matchEndTime);
    }



    // 경기 시간 계산
    private long calculateTime(LocalTime matchStartTIme, LocalTime matchEndTime) {
        return ChronoUnit.SECONDS.between(matchStartTIme, matchEndTime);
    }

    /* 예외 처리 */

    // 경기 시작 시간 null 금지
    private void validMatchStartTIme(LocalTime matchStartTime) {
        if (matchStartTime == null) {
            throw new IllegalArgumentException("시간을 입력해 주세요");
        }
    }

    // 경기 종료 시간 null 금지
    private void validMatchEndTIme(LocalTime matchEndTime) {
        if (matchEndTime == null) {
            throw new IllegalArgumentException("시간을 입력해 주세요");
        }
    }

    // 경기 종료 시간이 시작 시간보다 뒤인지 확인
    private void validMatchTime(LocalTime MatchStartTime, LocalTime MatchEndTime) {
        if (matchEndTime.isBefore(matchStartTime)) {
            throw new IllegalArgumentException("경기 종료 시간을 경기 시작 시간보다 앞에 있을 수 없습니다.");
        }
    }

    // 경기 시간이 30분 미만인지 확인
    private void validThirtyMin(LocalTime MatchStartTime, LocalTime MatchEndTime) {
        if (ChronoUnit.MINUTES.between(matchStartTime, MatchEndTime) < 30) {
            throw new IllegalArgumentException("경기 최소 시간은 30분입니다.");
        }
    }

    // 경기 시간이 4시간 초과인지 확인
    private void validOverTime(LocalTime MatchStartTime, LocalTime MatchEndTime) {
        if (ChronoUnit.HOURS.between(matchStartTime, MatchEndTime) > 4) {
            throw new IllegalArgumentException("경기 최대 시간은 4시간입니다.");
        }
    }

    private void validMatch(LocalTime matchStartTime, LocalTime matchEndTime) {
        validMatchStartTIme(matchStartTime);
        validMatchEndTIme(matchEndTime);
        validMatchTime(matchStartTime, matchEndTime);
        validThirtyMin(matchStartTime, matchEndTime);
        validOverTime(matchStartTime, matchEndTime);
    }

}
