package com.yfmf.footlog.match.model.enums;

import lombok.Getter;

@Getter
public enum MatchPlayerQuantity {
    PLAYER_QUANTITY_FOUR(4),
    PLAYER_QUANTITY_FIVE(5),
    PLAYER_QUANTITY_SIX(6),
    PLAYER_QUANTITY_SEVEN(7),
    PLAYER_QUANTITY_EIGHT(8),
    PLAYER_QUANTITY_NINE(9),
    PLAYER_QUANTITY_TEN(10),
    PLAYER_QUANTITY_ELEVEN(11);

    private int head;

    MatchPlayerQuantity() {
    }

    MatchPlayerQuantity(int head) {
        this.head = head;
    }

}
