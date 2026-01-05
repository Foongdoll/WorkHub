package com.foongdoll.portfolio.backend.core.annotation;

import com.foongdoll.portfolio.backend.core.logging.CustomLogger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ApiLoggingAspect {

    @Around("@annotation(apiLogging)")
    public Object logApi(
            ProceedingJoinPoint joinPoint,
            ApiLogging apiLogging
    ) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        String title = apiLogging.message().isBlank()
                ? className + "." + methodName
                : apiLogging.message();

        long start = System.currentTimeMillis();

        /* ===== ENTER ===== */
        if (apiLogging.enter()) {
            CustomLogger.section("API ENTER : " + title);

            if (apiLogging.params()) {
                CustomLogger.info("Parameters: " + Arrays.toString(joinPoint.getArgs()));
            }
        }

        try {
            Object result = joinPoint.proceed();

            /* ===== EXIT ===== */
            if (apiLogging.exit()) {
                long elapsed = System.currentTimeMillis() - start;
                CustomLogger.info("API EXIT  : " + title + " (" + elapsed + "ms)");
            }

            return result;

        } catch (Throwable ex) {
            long elapsed = System.currentTimeMillis() - start;
            CustomLogger.error(
                    "API ERROR : " + title + " (" + elapsed + "ms)",
                    ex
            );
            throw ex;
        }
    }
}
