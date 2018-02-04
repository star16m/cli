package star16m.utils.cli;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlerAdvice {
    @Around("execution(* org.springframework.boot.CommandLineRunner+.run(..))")
    public void handleException(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            System.err.println("<<ERROR>>" + e.getMessage());
        }
    }
}