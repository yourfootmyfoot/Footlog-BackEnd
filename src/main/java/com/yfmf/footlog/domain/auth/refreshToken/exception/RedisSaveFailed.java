package com.yfmf.footlog.domain.auth.refreshToken.exception;

import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import lombok.Getter;

@Getter
public class RedisSaveFailed extends ApplicationException {
    String message;

    public RedisSaveFailed(String message, String logMessage) {
        super(ErrorCode.REDIS_SAVE_FAILED, logMessage);
        this.message = message;
    }
}
