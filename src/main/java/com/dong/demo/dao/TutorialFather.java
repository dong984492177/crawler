package com.dong.demo.dao;

import com.dong.demo.model.CrawlerUrl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
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
@Component()
public class TutorialFather {
    @Value("${crawler.path}")
    String crawlerPath;
    /**
     * 读取 HTML 配置模板,方便写内容
     * @return
     */
    public Document readModel(){
        Document moBanDoc = null;
        Resource resource = new ClassPathResource("model.html");
        try {
            File file = resource.getFile();
            moBanDoc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            log.error("读取 model.html 失败",e);
        }
        return moBanDoc;
    }

    /**
     * 将抓取的节点A标签转成对应级别的P标签,主要为了在md形成目录树,不同节点用不同h标签
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

}
