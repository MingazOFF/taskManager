package ru.mingazoff.taskManager.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * The type Task aspect for logging.
 */
@Slf4j
@Aspect
@Component
public class TaskAspect {

    /**
     * Log before method execution.
     *
     * @param joinPoint the join point
     */
    @Before("@annotation(LogBefore)")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            log.info("Calling method:{}, arg(s):{}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
        } else {
            log.info("Calling method:{}", joinPoint.getSignature().toShortString());
        }
    }

    /**
     * Log after throwing in method.
     *
     * @param joinPoint the join point
     * @param exception the exception
     */
    @AfterThrowing(pointcut = "@annotation(LogAfterThrowing)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.info("Exception throwing in method:{}, message:{}", joinPoint.getSignature().toShortString(),
                exception.getMessage());
    }

    /**
     * Log after returning in method.
     *
     * @param joinPoint the join point
     * @param result    the result
     */
    @AfterReturning(pointcut = "@annotation(LogAfterReturning)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method:{} returning:{}", joinPoint.getSignature().toShortString(), result);
    }

    /**
     * Log around (before and after) execution method.
     *
     * @param joinPoint the join point
     * @return the object
     */
    @Around("@annotation(LogAround)")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            log.info("Calling method:{}, arg(s):{}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
        } else {
            log.info("Calling method:{}", joinPoint.getSignature().toShortString());
        }
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Exception throwing in method:{}, message:{}", joinPoint.getSignature().toShortString(),
                    e.getMessage());
            throw new RuntimeException();
        }
        long finish = System.currentTimeMillis();
        log.info("Execution time [method:{}] in ms:{}", joinPoint.getSignature().toShortString(), finish - start);
        return result;

    }
}
