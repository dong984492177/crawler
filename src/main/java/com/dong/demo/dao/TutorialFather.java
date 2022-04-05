package com.dong.demo.dao;

import cn.hutool.core.io.IoUtil;
import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.model.TutorialsNode;
import com.dong.demo.service.CrawlerUrlService;
import com.dong.demo.service.TutorialsMappingService;
import com.dong.demo.service.TutorialsNodeService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 爬虫父类通用方法
 *
 * @author Dong_Jia_Qi on 2021/12/19
 */
@Slf4j
@Component
public class TutorialFather {
    @Value("${crawler.path}")
    String crawlerPath;
    @Value("${crawler.md-path}")
    String crawlerMdPath;

    @Autowired
    public TutorialsNodeService tutorialsNodeService;
    @Autowired
    public CrawlerUrlService crawlerUrlService;
    @Autowired
    public TutorialsMappingService tutorialsMappingService;

    /**
     * 读取 HTML 配置模板,方便写内容
     * @return
     */
    public Document readModel(){
        Document moBanDoc = null;
        Resource resource = new ClassPathResource("model.html");
        try {
            // File file = resource.getFile();
            InputStream inputStream = resource.getInputStream();
            String read = IoUtil.read(inputStream, StandardCharsets.UTF_8);
            // moBanDoc = Jsoup.parse(file, "UTF-8");
            moBanDoc = Jsoup.parse(read, "UTF-8");
        } catch (IOException e) {
            log.error("读取 model.html 失败",e);
        }
        return moBanDoc;
    }

    /**
     * 将抓取的节点A标签转成对应级别的h标签,主要为了在md形成目录树,不同节点用不同h标签
     * @param crawlerUrl  先行处理目录过后的url信息
     * @return 追加后的 bodyElements
     */
    public Element updateAToHTag( CrawlerUrl crawlerUrl) {
        //目录中是拿到a标签 换成h标签 这样到 md 中就有目录了
        String hTag = "h"+ crawlerUrl.getUrlGrade();
        Element urlElement = crawlerUrl.getUrlElement().clone();
        urlElement = urlElement.selectFirst("a");
        String textUrl = crawlerUrl.getUrl();
        urlElement.attr("href",textUrl);
        urlElement.removeAttr("target").removeAttr("title").removeAttr("class");
        urlElement.tagName(hTag);
        return urlElement;
    }
    public void updateHToBTag(Element textElement){
        for (int i = 1; i <7 ; i++) {
            String hTag = "h"+i;
            Elements hElements = textElement.select(hTag);
            for (Element hElement : hElements) {
                hElement.tagName("b");
            }
        }
    }

    /**
     * 将节点下代码节点改为代码块
     * @param textElement
     * @param tag
     */
    public void updateToPreTag(Element textElement,String tag){
        Elements hElements = textElement.select(tag);
        for (Element hElement : hElements) {
            hElement.tagName("pre");
        }

    }

    /**
     * 将节点下给定标签去除样式
     * @param textElement
     * @param tag
     */
    public void tagRemoveStyle(Element textElement, String tag){
        Elements select = textElement.select(tag);
        for (Element element : select) {
            element.removeAttr("style");
            String text = element.text();
            element.text(text);
        }
    }
    public void tagRemoveAttr(Element textElement, String tag,String attr){
        Elements select = textElement.select(tag);
        for (Element element : select) {
            element.removeAttr(attr);
            String text = element.text();
            element.text(text);
        }
    }

    /**
     * 写HTML文件
     * @param name 教程名
     * @param mappingName 爬虫项目名
     * @param modelDoc  内容
     * @throws IOException
     */
    public void writeHTML(String name, String mappingName, Document modelDoc) throws IOException {
       Path path = Paths.get(crawlerPath + File.separator + mappingName);
       Path filePath = Paths.get(path.toString() + File.separator + name + ".html");
       //文件夹不存在
       if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
           log.info("创建文件夹路径 :" + path.toString());
           Files.createDirectories(path);
       }

       Files.write(filePath, modelDoc.toString().getBytes());
   }

    /**
     * 写md文件
     * 用的 node 的 h2m 项目 ,不知道如何实现调用,只能命令行
     * npm install h2m -g
     * @param name 教程名
     * @param mappingName 爬虫项目名
     * @throws IOException
     */
    public void writeMd(String name, String mappingName) throws IOException {
        Path path = Paths.get(crawlerPath + File.separator + mappingName);
        Path mdPath = Paths.get(crawlerMdPath + File.separator + mappingName);
        Path fileMdPath = Paths.get(mdPath.toString() + File.separator + name + ".md");
        //文件夹不存在
        if (!Files.exists(mdPath, LinkOption.NOFOLLOW_LINKS)) {
            log.info("创建文件夹路径 :" + mdPath.toString());
            Files.createDirectories(mdPath);
        }
        String os = System.getProperty("os.name");
        log.info(os);
        String cmd ="";
        if (os.toLowerCase().contains("windows".toLowerCase())) {
            cmd = String.format("cmd /c G: && cd %s && h2m -f \"%s\" > \"%s\"",path,  name + ".html",fileMdPath);
        }else if (os.toLowerCase().contains("linux".toLowerCase())){
            cmd = String.format("/bin/sh -c  cd %s && h2m -f \"%s\"  > \"%s\"",path,  name + ".html",fileMdPath);
        }
        log.info(cmd);
        try{
            Process pro=Runtime.getRuntime().exec(cmd);
        }catch(IOException e){
            log.error(e.getMessage(),e);
        }
    }

    /**
     * 将节点中的 图片标签用 src指定的路径全部转换为绝对路径
     * @param textElement
     */
    public void imgSrcToAbs(Element textElement) {
        Elements select = textElement.select("img[src]");
        for (Element element : select) {
            String src = element.attr("abs:src");
            element.attr("src",src);
        }
    }

    /**
     * 将节点中的 图片标签用 src指定的路径全部转换为绝对路径
     * @param textElement
     */
    public void imgHrefToAbs(Element textElement) {
        Elements select = textElement.select("img[href]");
        for (Element element : select) {
            String src = element.attr("abs:href");
            element.attr("href",src);
        }
    }
    /**
     * 将节点中的 图片标签用 src指定的路径全部转换为绝对路径
     * @param textElement
     */
    public void imgDataSrcToAbs(Element textElement) {
        Elements select = textElement.select("img[data-src]");
        for (Element element : select) {
            String src = element.attr("abs:data-src");
            element.attr("src",src);
        }
    }
    /**
     * 将节点中的 a 标签用 src指定的路径全部转换为绝对路径
     * @param textElement
     */
    public void aHrefToAbs(Element textElement) {
        Elements select = textElement.select("a[href]");
        for (Element element : select) {
            String src = element.attr("abs:href");
            element.attr("href",src);
        }
    }

    /**
     * 判断数据有没有进node 没有就进
     * @param id 爬虫项目编号
     * @param url 爬虫网站
     * @param fatherNodeName 爬虫名
     * @param fatherNode 对象
     * @return
     */
    protected TutorialsNode getTutorialsNode(int id, String url, String fatherNodeName, TutorialsNode fatherNode) {
        if (fatherNode == null) {
            fatherNode = new TutorialsNode();
            fatherNode.setName(fatherNodeName);
            fatherNode.setTutorialsStatus(-1);
            fatherNode.setParentId(-1);
            fatherNode.setUrl(url);
            fatherNode.setCrawleId(id);
            tutorialsNodeService.save(fatherNode);
            fatherNode = tutorialsNodeService.getDbByName(id, fatherNodeName);
        }
        return fatherNode;
    }

    public Element getTagByCrawlerUrl(CrawlerUrl crawlerUrl){
        Integer urlGrade = crawlerUrl.getUrlGrade();
        String urlText = crawlerUrl.getUrlText();
        String hTag = "h"+urlGrade;
        String tagStr = "<" + hTag + ">" + urlText + "</" + hTag + ">";
        Document document = Jsoup.parse(tagStr);
        Element element = document.selectFirst(hTag);
        return element;
    }

}
