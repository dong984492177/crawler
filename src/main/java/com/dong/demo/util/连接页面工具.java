package com.dong.demo.util;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
public class 连接页面工具 {
    Map<Object, Object> map = new HashMap();

    /**
     * 获得页面内容
     *
     * @param url 访问路径
     * @return
     */
    public static Document getDocument(String url) {
        log.info("访问 " + url);
        try {
            //50000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(50000).get();
        } catch (IOException e) {
            try {
                return Jsoup.connect(url).timeout(50000).get();
            } catch (IOException ex) {
                try {
                    return Jsoup.connect(url).timeout(50000).get();
                } catch (IOException exc) {
                    log.error(url + "访问失败", exc);
                }

            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("D:\\setting.properties"));
        props.setProperty("url", "http://www.liaoxuefeng.com");
        props.setProperty("language", "Java");
        props.store(new FileOutputStream("D:\\setting.properties"), "这是写入的properties注释");
    }
}
