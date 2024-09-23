package com.yfmf.footlog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "Unknown error", "예상치 못한 오류가 발생했습니다.", "UNKNOWN-001"),

    /*Club 도메인 예외*/
    INVALID_CLUB(HttpStatus.NOT_FOUND, "Invalid club", "해당하는 구단이 없습니다.", "CLUB-001"),
    DUPLICATED_CLUB(HttpStatus.CONFLICT, "Duplicated club", "해당 구단이 이미 존재합니다.", "CLUB-002"),
    ILLEGAL_CLUB_ARGUMENT(HttpStatus.BAD_REQUEST, "Illegal argument", "구단에 알맞은 인자값이 아닙니다.", "CLUB-003"),

    /*Auth 도메인 예외 */
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "login required", "로그인 후 이용이 가능합니다.", "AUTH-001");

    private final String description;
    private final HttpStatus status;
    private final String code;
    private final String errorType;

    ErrorCode(HttpStatus status, String errorType, String description, String code) {
        this.status = status;
        this.errorType = errorType;
        this.description = description;
        this.code = code;
    }
}
