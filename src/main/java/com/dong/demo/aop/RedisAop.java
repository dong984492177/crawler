package com.dong.demo.aop;

import com.dong.demo.util.AopUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * RedisUtil 的aop类
 *
 * @author Dong_Jia_Qi on 2022/1/25
 */
@Aspect
@Component
@Slf4j
public class RedisAop {
    @Pointcut("execution (* com.dong.demo.util.RedisUtils.*(..))")
    void all(){
    }
    @Before("all()")
    void allBefore(JoinPoint joinPoint){
        AopUtil.BeforeLog(joinPoint);
    }
}
