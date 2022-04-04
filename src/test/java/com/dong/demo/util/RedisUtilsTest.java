package com.dong.demo.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class RedisUtilsTest {
    @Autowired
    RedisUtils redisUtils;
    @Test
    void hmRemoveByKey() {
        redisUtils.hmRemoveByKey("tutorialsMapping:hash1", "More");
        redisUtils.hmRemoveByKey("tutorialsMapping:hash1", "廖雪峰官网");
    }

    @Test
    void hmRemoveAll() {
        redisUtils.hmRemoveAll("tutorialsMapping:hash2");
    }

    @Test
    void hmGetAll() {
        redisUtils.hmGetAll("tutorialsMapping:hash2");
    }
}