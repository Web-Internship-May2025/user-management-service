package com.example.user_management_service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String pointcut = "execution(* com.example.user_management_service.controller..*(..)) || execution(* com.example.user_management_service.service..*(..)) || execution(* com.example.user_management_service.repository..*(..))";

    @Around(pointcut)
    public Object logEverything(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();

        logger.info(">>> Enter: {}() with args = {}", methodName, args);

        Object result = null;
        try {
            result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.info("<<< Exit: {}() return = {} ({} ms)", methodName, result, duration);
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("Exception thrown in {}() with args = {}. Exception: {}", methodName, args, ex.toString(), ex);
            throw ex;
        }
    }
}
