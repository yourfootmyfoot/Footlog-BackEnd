package com.yfmf.footlog.domain.auth.exception;


import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import lombok.Getter;

@Getter
public class LoginRequiredException extends ApplicationException {
    private String message;

    public LoginRequiredException(String message, String logMessage) {
        super(ErrorCode.LOGIN_REQUIRED, logMessage, message);
        this.message = message;
    }
}
