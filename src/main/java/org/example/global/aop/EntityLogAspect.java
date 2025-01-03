package org.example.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class EntityLogAspect {

    @Around("execution(* org.example..*.domain.entity..*.*(..))")
    public Object logEntity(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.debug("[Entity] {}.{} => called", className, methodName);

        return joinPoint.proceed();
    }
}
