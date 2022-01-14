package com.dong.demo.service.impl;

import com.dong.demo.service.TutorialsMappingService;
import com.dong.demo.service.TutorialsService;
import com.dong.demo.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TutorialsServiceImplTest {
    @Autowired
    TutorialsMappingService tutorialsMappingService;
    @Autowired
    TutorialsService tutorialsService;
    @Autowired
    RedisUtils redisUtils;
    @Test
    void crawler() {
        tutorialsService.crawler(1,"java教程");
    }
    @Test
    void test(){
        String key = "tutorialsMapping:hash"+ 1;
        Set<Object> names = redisUtils.hmGetAll(key);
        for (Object name : names) {
            log.info("删除 name : "+ name);
            redisUtils.hmRemoveByKey(key,name);
        }
    }

}