package himanshu.fooddelevery.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Log before method execution
    @Before("execution(* himanshu.fooddelevery.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("➡️ Entering method: {}()", methodName);
    }

    // Log after method execution when successful
    @AfterReturning(pointcut = "execution(* himanshu.fooddelevery.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("⬅️ Exiting method: {}() with result: {}", methodName, result != null ? result : "void");
    }

    // Log when an exception is thrown
    @AfterThrowing(pointcut = "execution(* himanshu.fooddelevery.services.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();
        log.error("❌ Exception in method: {}() with error: {}", methodName, error.getMessage());
    }

    // Log execution time of method
    @Around("execution(* himanshu.fooddelevery.services.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info("⏱️ Method {} executed in {} ms", joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }
}
