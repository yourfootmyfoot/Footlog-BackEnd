package com.yfmf.footlog.domain.club.exception;

import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import lombok.Getter;

@Getter
public class ClubNotFoundException extends ApplicationException {
    String message;

    public ClubNotFoundException(String message, String logMessage) {
        super(ErrorCode.NOT_FOUND_CLUB, logMessage, message);
        this.message = message;
    }
}
