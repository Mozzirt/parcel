package com.mozzi.parcelpjt.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class TimeAop {

    @Around("@annotation(com.mozzi.parcelpjt.config.annotation.TimeLog)")
    public Object timeLogger(ProceedingJoinPoint joinPoint) throws Throwable {

        long bfTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long afTime = System.currentTimeMillis();

        long response = (afTime - bfTime)/1000;
        log.debug("[Response time] = {}초", response);

        if (response >= 10){
            log.warn("[Delayed Response time] = {}, {}초", joinPoint.getSignature(), response);
        }
        return proceed;
    }
}
