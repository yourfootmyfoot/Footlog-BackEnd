package com.yfmf.footlog.match.enums;

public enum QuarterQuantity {
    QUARTER_QUANTITY_ONE(1),
    QUARTER_QUANTITY_TWO(2),
    QUARTER_QUANTITY_THREE(3),
    QUARTER_QUANTITY_FOUR(4),
    QUARTER_QUANTITY_FIVE(5),
    QUARTER_QUANTITY_SIX(6),
    QUARTER_QUANTITY_SEVEN(7),
    QUARTER_QUANTITY_EIGHT(8);

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
