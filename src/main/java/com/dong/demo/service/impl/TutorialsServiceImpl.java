package com.dong.demo.service.impl;

import com.dong.demo.dao.TutorialsDao;
import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.model.TutorialsMapping;
import com.dong.demo.model.TutorialsNode;
import com.dong.demo.service.CrawlerUrlService;
import com.dong.demo.service.TutorialsMappingService;
import com.dong.demo.service.TutorialsNodeService;
import com.dong.demo.service.TutorialsService;
import com.dong.demo.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 教程爬虫实现类
 *
 * @author Dong_Jia_Qi on 2021/12/19
 */
@Service
@Slf4j
public class TutorialsServiceImpl implements TutorialsService {
    @Autowired
    Map<String , TutorialsDao> map ;
    @Autowired
    TutorialsMappingService tutorialsMappingService;
    @Autowired
    TutorialsNodeService tutorialsNodeService;
    @Autowired
    CrawlerUrlService crawlerUrlService;
    @Autowired
    RedisUtils redisUtils;
    @Override
    public boolean crawler(int id, String name) {
        TutorialsMapping tutorialsMapping = tutorialsMappingService.getById(id);
        if (tutorialsMapping == null) {
            return false;
        }
        String mappingClass = tutorialsMapping.getMappingClass();
        if (mappingClass==null&&mappingClass.isEmpty()) {
            log.info("id : {} 没有 mappingClass 数据",id);
            return false;
        }else{
            TutorialsDao tutorialsDao = map.get(mappingClass);
            TutorialsNode tutorialsNode = tutorialsNodeService.getByName(id, name);
            if (tutorialsNode == null) {
                log.info("项目id为 {} 中没有教程名为 {} 的数据", id,name);
                return  false;
            }

            int status = tutorialsNode.getTutorialsStatus();
            List<CrawlerUrl> list = null ;
            switch (status){
                case 0 :
                    list = crawlerBookmarks(id, name, tutorialsDao, tutorialsNode);
                    break;
                case 1 :
                    list = crawlerUrlService.getByIdAndName(id,name);
                    if (list.size()==0){
                        list = crawlerBookmarks(id, name, tutorialsDao, tutorialsNode);
                    }
                    break;
                default:
                    
            }
            boolean writeFlag = true;
            for (CrawlerUrl crawlerUrl : list) {
                Integer crawleStatus = crawlerUrl.getCrawleStatus();
                if (crawleStatus ==1){
                    continue;
                }
                boolean flag = tutorialsDao.crawlerMainBody(crawlerUrl);
                if (!flag){
                    try {
                        Thread.sleep(10*60*1000);
                    } catch (InterruptedException e) {
                        log.error("暂停时间出错",e);
                    }
                    flag = tutorialsDao.crawlerMainBody(crawlerUrl);
                }
                if (!flag){
                    writeFlag = false;
                }
            }
            if (writeFlag){
                //读 html 模板
                Document modelDoc = tutorialsDao.readModel();
                //主要放body
                Elements bodyElements = modelDoc.select("body");
                //标题头其实无所谓的  用不上
                Element titleElement = modelDoc.selectFirst("title");
                for (CrawlerUrl crawlerUrl :list){
                    bodyElements.append(crawlerUrl.getCrawlerText().toString());
                }
                try {
                    tutorialsDao.writeHTML(name, tutorialsMapping.getName(), modelDoc);
                } catch (IOException e) {
                    log.error("写文件异常",e);
                }
            }

        }


        return false;
    }

    private List<CrawlerUrl> crawlerBookmarks(int id, String name, TutorialsDao tutorialsDao, TutorialsNode tutorialsNode) {
        List<CrawlerUrl> list;
        String url = tutorialsNode.getUrl();
        Element div = tutorialsDao.crawler(url);
        list = new ArrayList<CrawlerUrl>();
        list = tutorialsDao.crawlerBookmarks(id, name,div, list);
        log.info("批量保存书签");
        for (CrawlerUrl crawlerUrl : list) {
            crawlerUrlService.saveOrUpdateByName(crawlerUrl);
        }
        log.info("修改爬虫节点的状态");
        if (list.size()> 0){
            tutorialsNode.setTutorialsStatus(1);
            tutorialsNodeService.saveOrUpdateByName(tutorialsNode);
        }
        return list;
    }


}
