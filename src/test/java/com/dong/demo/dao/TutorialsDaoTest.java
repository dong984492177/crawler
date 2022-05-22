package com.dong.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.service.CrawlerUrlService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
@Slf4j
class TutorialsDaoTest {
    @Qualifier("RunoobDaoImpl")
    @Autowired
    TutorialsDao tutorialsDao;
    @Autowired
    CrawlerUrlService crawlerUrlService;

    @Test
    void crawlerNode() {
        boolean b = tutorialsDao.crawlerNode(2, "");
        log.info("" + b);
    }

    @Test
    void crawler() {
        Element crawler = tutorialsDao.crawler("https://www.runoob.com/html/html-tutorial.html");
        log.info(crawler.toString());
        Elements select = crawler.select("a,h2");
        for (Element element : select) {
            log.info(element.toString());
        }
    }

    @Test
    void crawlerBookmarks() {
        Element crawler = tutorialsDao.crawler("https://www.runoob.com/html/html-tutorial.html");
        ArrayList<CrawlerUrl> list = new ArrayList<>();
        tutorialsDao.crawlerBookmarks(2, "学习 HTML", crawler, list);
    }

    @Test
    void crawlerMainBody() {
        CrawlerUrl crawle_order = crawlerUrlService.getOne(new QueryWrapper<CrawlerUrl>().eq("crawle_order", 1));
        tutorialsDao.crawlerMainBody(crawle_order);
    }
}