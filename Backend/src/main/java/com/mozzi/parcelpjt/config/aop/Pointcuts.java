package com.mozzi.parcelpjt.config.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.mozzi..parcelpjt.service..*(..))")
    public void allServicePointcut() {}

    @Pointcut("execution(* com.mozzi..parcelpjt.controller..*(..))")
    public void allControllerPointcut() {}

    @Deprecated
    @Pointcut("allServicePointcut() && allControllerPointcut()")
    public void allControllerAndServicePointcut() {}

    @Pointcut("execution(* com.mozzi..parcelpjt.controller.CryptController.*(..))")
    public void cryptoAuthPointcut() {}


}
