package com.yfmf.footlog.domain.auth.exception;

import com.yfmf.footlog.exception.ErrorCode;
import com.yfmf.footlog.exception.RootException;
import lombok.Getter;

@Getter
public class LoginRequiredException extends RootException {
    private String message;

    public LoginRequiredException(String message, String logMessage) {
        super(ErrorCode.LOGIN_REQUIRED, logMessage, message);
        this.message = message;
    }
}
