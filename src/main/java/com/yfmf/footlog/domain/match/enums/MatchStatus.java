package com.yfmf.footlog.domain.match.enums;

public enum MatchStatus {
    WAITING,    // 대기
    PENDING,    // 매치 주인이 수락 대기중
    ACCEPTED,   // 매치가 수락된 경우
    PLAYING,    // 경기 진행중
    FINISHED    // 종료
}