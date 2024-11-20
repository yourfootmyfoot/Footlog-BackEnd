package com.yfmf.footlog.domain.guest.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    PENDING(0),    // 대기중
    APPROVED(1),   // 승인됨
    REJECTED(2);   // 거절됨

    private final int order;

    ApplicationStatus(int order) {
        this.order = order;
    }
}