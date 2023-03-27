package com.challenge.aspect;

import java.lang.reflect.Method;
import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TiempoEjecucionProcessor {

    @Around("@annotation(TiempoEjecucion)")
    public Object medirTiempoEjecucion(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        Method method = getMethod(pjp);
        if(!Objects.isNull(method))
        	log.info("Tiempo de ejecución del método " + method.getName() + ": " + elapsedTime + " ms");
        return result;
    }
    private Method getMethod(ProceedingJoinPoint pjp) {
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), getParameterTypes(pjp));
        } catch (NoSuchMethodException ex) {
            log.warn("No se pudo obtener el método de la clase " + pjp.getTarget().getClass().getName());
        }
        return method;
    }

    private Class<?>[] getParameterTypes(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        return parameterTypes;
    }
}