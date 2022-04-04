package com.dong.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
@Slf4j
public class AopUtil {
    public AopUtil() {
    }

    /**
     * 通用的方法进来前记日志
     * @param joinPoint
     */
    public static void BeforeLog(JoinPoint joinPoint) {
        Object[] objs = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        String name = "获得方法名失败";
        try {
            Method method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            name = method.getName();
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        log.info("{} 进入{}方法, 传入参数{}", target.getClass().getName(), name, objs);
    }
}