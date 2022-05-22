package com.dong.demo.controller;

import com.dong.demo.model.TutorialsMapping;
import com.dong.demo.model.TutorialsNode;
import com.dong.demo.service.TutorialsMappingService;
import com.dong.demo.service.TutorialsNodeService;
import com.dong.demo.service.TutorialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author Dong_Jia_Qi on 2022/2/17
 */
@RestController
@Slf4j
public class VueController {
    @Autowired
    TutorialsMappingService tutorialsMappingService;
    @Autowired
    TutorialsNodeService tutorialsNodeService;
    @Autowired
    TutorialsService tutorialsService;
    @Value("${crawler.path}")
    String crawlerPath;
    @Value("${crawler.md-path}")
    String crawlerMdPath;

    @RequestMapping("getMappingByType1")//获得基本爬虫状态的项目
    public List<TutorialsMapping> getMappingByType1()
    {
        return tutorialsMappingService.getByType(1);
    }


    @RequestMapping("crawlerNodeStart1/{id}")//爬项目网站节点
    public boolean crawlerNodeStart1(@PathVariable(name = "id") int id) {
        return tutorialsService.crawlerNode(id, "");
    }

    @RequestMapping("getDbNodeByCrawleId/{id}")//强行获得爬虫任务数据
    public List<TutorialsNode> getDbNodeByCrawleId(@PathVariable(name = "id") int id) {
        return tutorialsNodeService.getDbByCrawleId(id);
    }

    @RequestMapping("getNodeByCrawleId/{id}")//从缓存中获得爬虫数据
    public List<TutorialsNode> getNodeByCrawleId(@PathVariable(name = "id") int id) {
        return tutorialsNodeService.getByCrawleId(id);
    }

    @RequestMapping("getNodeDbByName")//从缓存中获得单条爬虫数据
    public TutorialsNode getNodeDbByName(@RequestBody Map map) {
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        return tutorialsNodeService.getDbByName(id, name);
    }

    @RequestMapping("getNodeByName")//从缓存中获得单条爬虫数据
    public TutorialsNode getNodeByName(@RequestBody Map map) {
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        return tutorialsNodeService.getByName(id, name);
    }


    @RequestMapping("crawlerStart")//开始爬虫
    public boolean crawlerStart(@RequestBody Map map) {
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        return tutorialsService.crawler(id, name);
    }

    @RequestMapping("crawlerDownload")//下载文件
    public String crawlerDownload(HttpServletResponse response, @RequestBody Map map) {
        int id = (int) map.get("id");
        TutorialsMapping tutorialsMapping = tutorialsMappingService.getById(id);
        String mappingName = tutorialsMapping.getName();
        String name = (String) map.get("name");
        int type = (int) map.get("type");
        String FilePath = "";
        switch (type) {
            case 1: {
                FilePath = crawlerPath + File.separator + mappingName + File.separator + name + ".html";
                break;
            }
            case 2: {
                FilePath = crawlerMdPath + File.separator + mappingName + File.separator + name + ".md";
                break;
            }

        }
        if ("".equals(FilePath)) {
            return "文件请求有问题,重新尝试";
        }
        File file = new File(FilePath);
        if (!file.exists()) {
            return String.format("下载文件%s不存在", mappingName + " _ " + name);
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        log.info(file.getName());
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("{}", e);
            return "下载失败";
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("{}", e);
            return "下载失败";
        }
        return "下载成功";
    }
}
