package com.dong.demo.dao;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
@Slf4j
class TutorialFatherTest {
    TutorialFather tutorialFather = new TutorialFather();

    @Test
    void readModel() {
        Document document = tutorialFather.readModel();
        log.info(document.toString());
    }
    @Test
    public void  test(){
        String a ="<a href=\"https://www.liaoxuefeng.com/wiki/1252599548343744\" class=\"x-wiki-index-item\">Java教程</a>";
//        String a =null;
        Document parse = Jsoup.parse(a);
        Element body = parse.body();
        log.info(body.text());
        log.info(JSON.toJSONString(body));
    }
}