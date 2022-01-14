package com.dong.demo.dao;

import com.dong.demo.model.CrawlerUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

/**
 * 单网站爬虫教程接口
 *
 * @author Dong_Jia_Qi on 2021/12/19
 */

public interface TutorialsDao {
    /**
     * 爬虫获得教程书签
     * @param url 爬教链接
     * @return 教程书签 div 块
     */
    public Element crawler(String url );
    /**
     * 书签爬虫
     * @param crawleId 爬虫项目iD
     * @param crawleName 爬虫教程id
     * @param div 书签部分
     * @param list 用来存放书签
     * @return 整理好的书签信息
     */
    public List<CrawlerUrl> crawlerBookmarks(Integer crawleId, String crawleName, Element div, List<CrawlerUrl> list);

    /**
     * 内容主体爬虫
     * @param crawlerUrl  url信息体
     * @return 整理好的 bodyElements
     */
    public boolean crawlerMainBody(CrawlerUrl crawlerUrl);

    /**
     * 读取 HTML 配置模板,方便写内容
     * @return
     */
    Document readModel();

    /**
     * 写HTML文件
     * @param name 教程名
     * @param mappingName 爬虫项目名
     * @param modelDoc  内容
     * @throws IOException
     */
    public void writeHTML(String name, String mappingName, Document modelDoc) throws IOException;

    public boolean crawlerNode(int id);
}
