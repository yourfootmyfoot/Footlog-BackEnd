package com.yfmf.footlog.match.command.domain.model.enums;

import lombok.Getter;

@Getter
public enum MatchPlayerQuantity {
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11);

    private int head;

    MatchPlayerQuantity() {
    }

    MatchPlayerQuantity(int head) {
        this.head = head;
    }

}
