package com.mozzi.parcelpjt.config.aop;

import com.mozzi.parcelpjt.controller.exception.custom.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.mozzi.parcelpjt.config.response.CommonConstants.IS_ADMIN;

@Slf4j
@Aspect
public class AuthAop {
    // 포인트컷 컨트롤러 지정으로 해당 컨트롤러 ControllerAdvice ExceptionHandler 타도록 설정
    @Before("com.mozzi.parcelpjt.config.aop.Pointcuts.cryptoAuthPointcut()")
    public void unAuthorizedExceptionThrower(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request.getHeader("X-AUTH-ADMIN") == null || !request.getHeader("X-AUTH-ADMIN").equals(IS_ADMIN)){
            log.warn("[UnauthorizedException caused] = [ {} ]", joinPoint.getSignature());
            throw new UnauthorizedException(request.getHeader("X-AUTH-ADMIN"));
        }
    }
}
