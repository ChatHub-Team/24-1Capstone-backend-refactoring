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
public class RepositoryLogAspect {

    @Around("execution(* org.example..*.repository..*.*(..))")
    public Object logRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        log.info("[Repository] {}.{} => parameters: {}",
                className, methodName, joinPoint.getArgs());

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("[Repository] {}.{} => time: {}ms",
                className, methodName, stopWatch.getTotalTimeMillis());

        return result;
    }
}
