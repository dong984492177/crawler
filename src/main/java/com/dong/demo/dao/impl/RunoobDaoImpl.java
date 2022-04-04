package com.dong.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dong.demo.dao.TutorialFather;
import com.dong.demo.dao.TutorialsDao;
import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.model.TutorialsMapping;
import com.dong.demo.model.TutorialsNode;
import com.dong.demo.util.连接页面工具;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 菜鸟教程爬虫实现
 * @author Dong_Jia_Qi on 2022/3/17
 */

@Component("RunoobDaoImpl")
@Slf4j
public class RunoobDaoImpl extends TutorialFather implements TutorialsDao {

    @Override
    public boolean crawlerNode(int id, String appendStr) {
        //col middle-column-home
        TutorialsMapping tutorialsMapping = tutorialsMappingService.getById(id);
        String url = tutorialsMapping.getUrl();
        Document document = 连接页面工具.getDocument(url);
        String fatherNodeName = tutorialsMapping.getName();
        TutorialsNode fatherNode = tutorialsNodeService.getDbByName(id,fatherNodeName );
        fatherNode = getTutorialsNode(id, url, fatherNodeName, fatherNode);
        Element element = document.selectFirst(".col.middle-column-home");
        Elements aElements = element.select("a");
        for (Element aElement : aElements) {
            Element nameElement = aElement.selectFirst("h4");
            String text = nameElement.text();
            String nodeUrl = aElement.attr("abs:href");
            String name = text.replaceAll("【", "").replaceAll("】", "");
            TutorialsNode tutorialsNode = new TutorialsNode();
            tutorialsNode.setName(name);
            tutorialsNode.setUrl(nodeUrl);
            tutorialsNode.setCrawleId(id);
            tutorialsNode.setParentId(fatherNode.getId());
            List<TutorialsNode> list = tutorialsNodeService.list(new QueryWrapper<>(tutorialsNode));
            if (list.size()==0) {
                tutorialsNode.setTutorialsStatus(0);
            }else {
                tutorialsNode.setTutorialsStatus(list.get(0).getTutorialsStatus());
            }

            tutorialsNodeService.saveOrUpdateByName(tutorialsNode);
        }
        Element element2 = document.selectFirst("#manual");
        Elements aElements2 = element2.select("a");
        for (Element aElement : aElements2) {
            String text = aElement.text()+"手册";
            text = text.replace("手册手册", "手册");
            String nodeUrl = aElement.attr("abs:href");
            TutorialsNode tutorialsNode = new TutorialsNode();
            tutorialsNode.setName(text);
            tutorialsNode.setUrl(nodeUrl);
            tutorialsNode.setCrawleId(id);
            tutorialsNode.setParentId(fatherNode.getId());
            List<TutorialsNode> list = tutorialsNodeService.list(new QueryWrapper<>(tutorialsNode));
            if (list.size()==0) {
                tutorialsNode.setTutorialsStatus(0);
            }else {
                tutorialsNode.setTutorialsStatus(list.get(0).getTutorialsStatus());
            }

            tutorialsNodeService.saveOrUpdateByName(tutorialsNode);
        }
        return true;
    }

    @Override
    public Element crawler(String url) {
        Element div = null;
        Document document = 连接页面工具.getDocument(url);
        Element element = document.selectFirst("#leftcolumn");
        div = element.selectFirst("div");
        return div;
    }

    @Override
    public List<CrawlerUrl> crawlerBookmarks(Integer crawleId, String crawleName, Element div, List<CrawlerUrl> list) {
        Elements select = div.select("a,h2");
        //菜鸟教程基础教程无h2标签,特加一个
        CrawlerUrl crawlerUrl = new CrawlerUrl();
        crawlerUrl.setCrawleId(crawleId);
        crawlerUrl.setCrawleName(crawleName);
        crawlerUrl.setUrlText(crawleName + " 基础");
        crawlerUrl.setUrlGrade(2);
        crawlerUrl.setCrawleOrder(list.size()+1);
        crawlerUrl.setCrawleStatus(0);
        list.add(crawlerUrl);
        //开始遍历
        for (Element element : select) {
            Tag tag = element.tag();
            String tagName = tag.getName();
            String text = element.text();
            if ("h2".equals(tagName)) {
                CrawlerUrl h2CrawlerUrl = new CrawlerUrl();
                h2CrawlerUrl.setCrawleId(crawleId);
                h2CrawlerUrl.setCrawleName(crawleName);
                h2CrawlerUrl.setUrlText(text);
                h2CrawlerUrl.setUrlGrade(2);
                h2CrawlerUrl.setCrawleOrder(list.size()+1);
                h2CrawlerUrl.setCrawleStatus(0);
                list.add(h2CrawlerUrl);
                continue;
            }
            if ("a".equals(tagName)) {
                String href = element.attr("abs:href");
                CrawlerUrl aCrawlerUrl = new CrawlerUrl();
                aCrawlerUrl.setCrawleId(crawleId);
                aCrawlerUrl.setCrawleName(crawleName);
                aCrawlerUrl.setUrlText(text);
                aCrawlerUrl.setUrl(href);
                aCrawlerUrl.setUrlElement(element);
                aCrawlerUrl.setUrlGrade(3);
                aCrawlerUrl.setCrawleOrder(list.size()+1);
                aCrawlerUrl.setCrawleStatus(0);
                list.add(aCrawlerUrl);
                continue;
            }
        }
        return list;
    }

    @Override
    public boolean crawlerMainBody(CrawlerUrl crawlerUrl) {
        try {
            Integer urlGrade = crawlerUrl.getUrlGrade();
            switch (urlGrade){
                case 2 : {
                    Element tagByCrawlerUrl = getTagByCrawlerUrl(crawlerUrl);
                    crawlerUrl.setCrawlerText(tagByCrawlerUrl);
                    crawlerUrl.setCrawleStatus(1);
                    return true;
                }
                case 3 : {
                    Element urlElement = updateAToHTag(crawlerUrl);
                    String url1 = crawlerUrl.getUrl();
                    Document textDoc = 连接页面工具.getDocument(url1);
                    if (textDoc != null){
                        Element textElement = textDoc.selectFirst(".article-body");
                        imgSrcToAbs(textElement);
                        imgHrefToAbs(textElement);
                        aHrefToAbs(textElement);
                        imgDataSrcToAbs(textElement);
                        updateHToBTag(textElement);
                        updateToPreTag(textElement,".example_code");
                        tagRemoveAttr(textElement,"pre","class");
                        // tagRemoveAttr(textElement,"span","style");
                        // tagRemoveAttr(textElement,"td","style");


                        Document document = Jsoup.parse(urlElement.toString() + textElement.toString());
                        Element element = document.body();
                        crawlerUrl.setCrawlerText(element);
                        crawlerUrl.setCrawleStatus(1);
                        return true;
                    }
                    else {
                        crawlerUrl.setCrawleStatus(3);
                        return false;
                    }
                }
                default:{
                    crawlerUrl.setCrawleStatus(3);
                    return false;
                }
            }
        } catch (Exception e) {
            crawlerUrl.setCrawleStatus(4);
            crawlerUrl.setErrDescribe(e.getMessage());
            log.error(e.getMessage(),e);
            return false;
        }finally {
            log.info("爬虫 {} {}  {}  结束" ,crawlerUrl.getCrawleId() ,crawlerUrl.getCrawleName(),crawlerUrl.getCrawleOrder());
        }
    }
}
