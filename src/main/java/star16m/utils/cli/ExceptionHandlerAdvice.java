package star16m.utils.cli;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ExceptionHandlerAdvice {
    @Around("execution(* org.springframework.boot.CommandLineRunner+.run(..))")
    public void handleException(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
    }
}