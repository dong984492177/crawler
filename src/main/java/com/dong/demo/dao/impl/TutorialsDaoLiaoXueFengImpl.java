package com.dong.demo.dao.impl;

import com.dong.demo.dao.TutorialFather;
import com.dong.demo.dao.TutorialsDao;
import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.model.TutorialsMapping;
import com.dong.demo.model.TutorialsNode;
import com.dong.demo.service.CrawlerUrlService;
import com.dong.demo.service.TutorialsMappingService;
import com.dong.demo.service.TutorialsNodeService;
import com.dong.demo.util.连接页面工具;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 廖雪峰官网爬虫实现
 *
 * @author Dong_Jia_Qi on 2021/12/19
 */

@Component("TutorialsDaoLiaoXueFengImpl")
@Slf4j
public class TutorialsDaoLiaoXueFengImpl extends TutorialFather implements TutorialsDao  {


    @Autowired
    TutorialsNodeService tutorialsNodeService;
    @Autowired
    CrawlerUrlService crawlerUrlService;
    @Autowired
    TutorialsMappingService tutorialsMappingService;

    @Override
    public Element crawler(String url ) {
        Element div = null;
        Document document = 连接页面工具.getDocument(url);
        Element element = document.selectFirst("#x-wiki-index");
        div = element.selectFirst("div");
        return div;
    }
    @Override
    public boolean crawlerNode(int id) {
        TutorialsMapping tutorialsMapping = tutorialsMappingService.getById(id);
        String url = tutorialsMapping.getUrl();
        Document document = 连接页面工具.getDocument(url);
        //log.info(document.toString());
        Element element = document.selectFirst(".uk-navbar-nav.uk-hidden-small");
        Elements aElements = element.select("a");
        log.info(aElements.toString());
        String fatherNodeName = tutorialsMapping.getName();
        TutorialsNode fatherNode = tutorialsNodeService.getDbByName(id,fatherNodeName );
        if (fatherNode == null) {
            fatherNode = new TutorialsNode();
            fatherNode.setName(fatherNodeName);
            fatherNode.setTutorialsStatus(-1);
            fatherNode.setParentId(-1);
            fatherNode.setUrl(url);
            tutorialsNodeService.save(fatherNode);
            fatherNode = tutorialsNodeService.getDbByName(id,fatherNodeName );
        }
        for (Element aElement : aElements) {
            String text = aElement.text();
            if (text.contains("文章")||text.contains("问答")||text.contains("More")){
                continue;
            }
            String nodeUrl = aElement.attr("abs:href");
            TutorialsNode tutorialsNode = new TutorialsNode();
            tutorialsNode.setName(text);
            tutorialsNode.setUrl(nodeUrl);
            tutorialsNode.setTutorialsStatus(0);
            tutorialsNode.setCrawleId(id);
            tutorialsNode.setParentId(fatherNode.getId());
            tutorialsNodeService.saveOrUpdateByName(tutorialsNode);
        }
        return false;
    }



    @Override
    public List<CrawlerUrl> crawlerBookmarks(Integer crawleId, String crawleName, Element div, List<CrawlerUrl> list) {
        String depth = div.attr("depth");
        Integer urlGrade = Integer.valueOf(depth)+1;
        Elements elements = div.select(">a");
        //拿到标题连接
        for (Element element:elements) {
            String href = element.attr("abs:href");
            String text = element.text();
            CrawlerUrl crawlerUrl = new CrawlerUrl();
            crawlerUrl.setCrawleId(crawleId);
            crawlerUrl.setCrawleName(crawleName);
            crawlerUrl.setUrlText(text);
            crawlerUrl.setUrl(href);
            crawlerUrl.setUrlElement(element);
            crawlerUrl.setUrlGrade(urlGrade);
            crawlerUrl.setCrawleOrder(list.size()+1);
            crawlerUrl.setCrawleStatus(0);
            list.add(crawlerUrl);
        }
        //拿到子标签div
        Elements divSelect = div.select(">div");
        for (Element element : divSelect) {
            crawlerBookmarks(crawleId,crawleName,element,list);
        }
        return  list;
    }

    @Override
    public boolean crawlerMainBody(CrawlerUrl crawlerUrl) {
        try {
            Element urlElement = updateAToHTag(crawlerUrl);
            String url1 = crawlerUrl.getUrl();
            Document textDoc = 连接页面工具.getDocument(url1);
            if (textDoc != null){
                //获得核心内容
                Element textElement = textDoc.selectFirst(".x-wiki-content");
                imgSrcToAbs(textElement);
                imgHrefToAbs(textElement);
                aHrefToAbs(textElement);
                imgDataSrcToAbs(textElement);
                updateHToBTag(textElement);
                Document document = Jsoup.parse(urlElement.toString() + textElement.toString());
                Element element = document.body();
                crawlerUrl.setCrawlerText(element);
                crawlerUrl.setCrawleStatus(1);
                return true;
            }else {
                crawlerUrl.setCrawleStatus(3);
                return false;
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
