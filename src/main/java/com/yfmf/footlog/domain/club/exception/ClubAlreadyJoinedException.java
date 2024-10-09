package com.yfmf.footlog.domain.club.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 409 Conflict 상태 코드로 반환되는 예외 클래스
@ResponseStatus(HttpStatus.CONFLICT)
public class ClubAlreadyJoinedException extends RuntimeException {
    public ClubAlreadyJoinedException(String message, String details) {
        super(message + " Details: " + details);
    }
}
