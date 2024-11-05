package com.yfmf.footlog.domain.club.exception;

import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateJoinRequestException extends ApplicationException {
    public DuplicateJoinRequestException() {
        super(ErrorCode.DUPLICATE_JOIN_REQUEST, ErrorCode.DUPLICATE_JOIN_REQUEST.getDescription());
    }
}