package com.yfmf.footlog.domain.ask.enums;

public enum AskCategory {
    USER("유저 문의"),
    CLUB("클럽 문의"),
    DISABLE("장애 문의"),
    ETC("기타 문의");

    private final String categoryName;

    AskCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
