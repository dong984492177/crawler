package com.dong.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dong.demo.dao.TutorialsDao;
import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.service.CrawlerUrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TutorialsDaoLiaoXueFengImplTest {
    @Qualifier("TutorialsDaoLiaoXueFengImpl")
    @Autowired
    TutorialsDao tutorialsDao;
    @Autowired
    CrawlerUrlService crawlerUrlService;

    @Test
    void crawlerMainBody() {
        CrawlerUrl crawle_order = crawlerUrlService.getOne(new QueryWrapper<CrawlerUrl>().eq("crawle_order", 1));
        tutorialsDao.crawlerMainBody(crawle_order);
    }

    @Test
    void crawlerNode() {
        boolean b = tutorialsDao.crawlerNode(1, "");
        log.info("" + b);
    }
}