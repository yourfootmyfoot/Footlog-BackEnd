package com.yfmf.footlog.domain.club.exception;

import com.yfmf.footlog.exception.ErrorCode;
import com.yfmf.footlog.exception.RootException;
import lombok.Getter;

@Getter
public class ClubNotFoundException extends RootException {
    String message;

    public ClubNotFoundException(String message, String logMessage) {
        super(ErrorCode.INVALID_CLUB, logMessage, message);
        this.message = message;
    }
}
