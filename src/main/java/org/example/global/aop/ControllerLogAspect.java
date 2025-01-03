package org.example.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    @Around("execution(* org.example..*.presentation..*.*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        log.info("[Controller] {}.{} => request: {}",
                className, methodName, joinPoint.getArgs());

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("[Controller] {}.{} => response: {}, time: {}ms",
                className, methodName, result, stopWatch.getTotalTimeMillis());

        return result;
    }
}