package com.yfmf.footlog.domain.club.exception;

import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import lombok.Getter;

@Getter
public class IllegalClubArgumentException extends ApplicationException {
    String message;

    public IllegalClubArgumentException(String message, String logMessage) {
        super(ErrorCode.INVALID_CLUB, logMessage, message);
        this.message = message;
    }
}
