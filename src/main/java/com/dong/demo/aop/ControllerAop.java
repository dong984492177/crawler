package com.dong.demo.aop;

import com.dong.demo.util.AopUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 控制层的aop
 *
 * @author Dong_Jia_Qi on 2022/3/17
 */

@Aspect
@Component
@Slf4j
public class ControllerAop {
    @Pointcut("execution (* com.dong.demo.controller.*.*(..))")
    void all() {
    }

    @Before("all()")
    void allBefore(JoinPoint joinPoint) {
        AopUtil.BeforeLog(joinPoint);
    }
}
