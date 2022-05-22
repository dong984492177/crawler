package com.dong.demo.aop;

import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.service.CrawlerUrlService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 爬虫接口动态代理
 *
 * @author Dong_Jia_Qi on 2021/12/31
 */
@Aspect
@Component
@Slf4j
public class TutorialsAop {
    @Autowired
    CrawlerUrlService crawlerUrlService;

    @Pointcut("execution (* com.dong.demo.dao.TutorialsDao.crawler(..))")
    void crawler() {
    }

    @Pointcut("execution (* com.dong.demo.dao.TutorialsDao.crawlerBookmarks(..))")
    void crawlerBookmarks() {
    }

    @Pointcut("execution (* com.dong.demo.dao.TutorialsDao.crawlerMainBody(..))")
    void crawlerMainBody() {
    }

    @Pointcut("execution (* com.dong.demo.dao.TutorialsDao.readModel(..))")
    void readModel() {
    }

    @Pointcut("execution (* com.dong.demo.dao.TutorialsDao.writeHTML(..))")
    void writeHTML() {
    }

    @After("crawlerMainBody()")
    void crawlerMainBodyAfter(JoinPoint joinPoint) {
        log.info("保存CrawlerUrl");
        Object[] objs = joinPoint.getArgs();
        for (Object obj : objs) {
            if (obj instanceof CrawlerUrl) {
                CrawlerUrl crawlerUrl = (CrawlerUrl) obj;
                crawlerUrlService.saveOrUpdateByName(crawlerUrl);
            }
        }

    }

}
