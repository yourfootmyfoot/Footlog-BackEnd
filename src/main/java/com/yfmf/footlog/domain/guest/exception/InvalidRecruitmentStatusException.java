package com.yfmf.footlog.domain.guest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRecruitmentStatusException extends RuntimeException {
    public InvalidRecruitmentStatusException(String message) {
        super(message);
    }
}
