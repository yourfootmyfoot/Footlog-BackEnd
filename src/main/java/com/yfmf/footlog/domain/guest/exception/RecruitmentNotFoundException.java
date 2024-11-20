package com.yfmf.footlog.domain.guest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecruitmentNotFoundException extends RuntimeException {
    public RecruitmentNotFoundException(String message) {
        super(message);
    }
}
