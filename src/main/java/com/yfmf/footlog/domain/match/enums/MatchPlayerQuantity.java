package com.yfmf.footlog.domain.match.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

    private final int head;

    MatchPlayerQuantity(int head) {
        this.head = head;
    }

    @JsonValue
    public int getHead() {
        return head;
    }

    @JsonCreator
    public static MatchPlayerQuantity fromValue(String value) {
        try {
            int intValue = Integer.parseInt(value);
            for (MatchPlayerQuantity quantity : MatchPlayerQuantity.values()) {
                if (quantity.head == intValue) {
                    return quantity;
                }
            }
        } catch (NumberFormatException e) {
        }
        throw new IllegalArgumentException("Invalid MatchPlayerQuantity value: " + value);
    }
}
