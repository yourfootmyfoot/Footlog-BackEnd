package com.yfmf.footlog.match.command.domain.model.enums;

public enum QuarterQuantity {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private int quarterValue;

    QuarterQuantity() {
    }

    QuarterQuantity(int quarterValue) {
        this.quarterValue = quarterValue;
    }

    public int getQuarterQuantityValue() {
        return quarterValue;
    }
}
