package com.dong.demo.dao;

import com.alibaba.fastjson.JSON;
import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.service.CrawlerUrlService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@Slf4j
@SpringBootTest
class TutorialFatherTest {
    @Autowired
    TutorialFather tutorialFather;
    @Autowired
    CrawlerUrlService crawlerUrlService;

    @Test
    void readModel() {
        Document document = tutorialFather.readModel();
        log.info(document.toString());
    }

    @Test
    public void test() {
        String a = "<a href=\"https://www.liaoxuefeng.com/wiki/1252599548343744\" class=\"x-wiki-index-item\">Java教程</a>";
//        String a =null;
        Document parse = Jsoup.parse(a);
        Element body = parse.body();
        log.info(body.text());
        log.info(JSON.toJSONString(body));
    }

    @Test
    void writeMd() {
        String name = "java教程";
        List<CrawlerUrl> list = crawlerUrlService.getByIdAndName(1, name);
        //读 html 模板
        Document modelDoc = tutorialFather.readModel();
        //主要放body
        Elements bodyElements = modelDoc.select("body");
        //标题头
        Element titleElement = modelDoc.selectFirst("title");
        titleElement.text(name);

        for (CrawlerUrl crawlerUrl : list) {
            bodyElements.append(crawlerUrl.getCrawlerText().toString());
        }
        try {
            tutorialFather.writeMd(name, "廖雪峰官网");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    void getTagByCrawlerUrl() {
        CrawlerUrl crawlerUrl = new CrawlerUrl();
        crawlerUrl.setCrawleId(2);
        crawlerUrl.setCrawleName("菜鸟教程");
        crawlerUrl.setUrlText("html" + " 基础");
        crawlerUrl.setUrlGrade(2);
        crawlerUrl.setCrawleOrder(1);
        crawlerUrl.setCrawleStatus(0);
        Element tagByCrawlerUrl = tutorialFather.getTagByCrawlerUrl(crawlerUrl);
        log.info(tagByCrawlerUrl.toString());
    }

    @Test
    void testWriteMd() {
        String name = "学习 Go";
        try {
            tutorialFather.writeMd(name, "菜鸟教程");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}