package org.example.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
public class UserActionAspect {
    @After("@annotation(TrackUserAction)")
    public void logUserAction(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        Object[] argsMethod = joinPoint.getArgs();

        System.out.println("Вызван метод: " + methodName);
        System.out.println("Параметры: " + methodName);
        System.out.println("Время вызова: " + Instant.now());
    }
}
