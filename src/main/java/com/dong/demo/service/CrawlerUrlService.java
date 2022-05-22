package com.dong.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.demo.model.CrawlerUrl;

import java.util.List;

/**
 * @author DONG
 * @description 针对表【crawler_url(爬虫url)】的数据库操作Service
 * @createDate 2021-12-27 14:07:57
 */
public interface CrawlerUrlService extends IService<CrawlerUrl> {
    /**
     * 通过项目id 和教程名称 查内容
     *
     * @param id   项目id
     * @param name 教程名称
     * @return 爬虫相关内容
     */
    public List<CrawlerUrl> getByIdAndName(int id, String name);

    public boolean saveOrUpdateByName(CrawlerUrl crawlerUrl);
}
