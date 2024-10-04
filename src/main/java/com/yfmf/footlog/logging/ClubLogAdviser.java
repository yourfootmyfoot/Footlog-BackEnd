package com.yfmf.footlog.logging;

import com.yfmf.footlog.domain.club.entity.Club;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class ClubLogAdviser {

    @Around("Pointcuts.clubLogPointcut()")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = proceedingJoinPoint.getArgs();

        Long clubId = (Long) args[0];  // 첫 번째 매개변수 (clubId)

        // 메서드 실행
        Object result = proceedingJoinPoint.proceed();

        Club club = (Club) result;  // 리턴된 구단 정보
        log.info("구단 조회: 구단 ID={}, 구단 정보={}", clubId, club);

        return result;
    }
}
