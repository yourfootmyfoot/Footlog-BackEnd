package com.yfmf.footlog.domain.club.exception;

import com.yfmf.footlog.exception.ErrorCode;
import com.yfmf.footlog.exception.RootException;
import lombok.Getter;

@Getter
public class ClubDuplicatedException extends RootException {
    String message;

    public ClubDuplicatedException(String message, String logMessage) {
        super(ErrorCode.INVALID_CLUB, logMessage, message);
        this.message = message;
    }
}
