package com.dong.demo;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 测试接口
 *
 * @author Dong_Jia_Qi on 2022/1/3
 */
@Slf4j
@SpringBootTest
public class Test001 {
    @Value("${crawler.path}")
    String crawlerPath;
    @Test
    void test(){
        String mappingName = "廖雪峰官网";
        String name = "java教程";
        Path path = Paths.get(crawlerPath + File.separator + mappingName);
        Path filePath = Paths.get(path.toString() + File.separator + name + ".html");
        File file = filePath.toFile();

    }
}
