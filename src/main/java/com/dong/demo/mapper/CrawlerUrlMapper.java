package com.dong.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.demo.model.CrawlerUrl;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DONG
 * @description 针对表【crawler_url(爬虫url)】的数据库操作Mapper
 * @createDate 2021-12-27 14:07:57
 * @Entity com.dong.demo.model.CrawlerUrl
 */
@Mapper
public interface CrawlerUrlMapper extends BaseMapper<CrawlerUrl> {

}




