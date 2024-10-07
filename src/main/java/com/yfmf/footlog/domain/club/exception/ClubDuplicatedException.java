package com.yfmf.footlog.domain.club.exception;

import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import lombok.Getter;

@Getter
public class ClubDuplicatedException extends ApplicationException {
    String message;

    public ClubDuplicatedException(String message, String logMessage) {
        super(ErrorCode.DUPLICATED_CLUB, logMessage, message);
        this.message = message;
    }
}
