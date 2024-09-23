package com.yfmf.footlog.domain.auth.utils;

import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentMemberId() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        if(name.equals("anonymousUser")) {
            throw new ApplicationException(ErrorCode.ANONYMOUS_USER);
        }

        return Long.parseLong(name);
    }
}
