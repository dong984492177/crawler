package com.dong.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.demo.mapper.CrawlerUrlMapper;
import com.dong.demo.model.CrawlerUrl;
import com.dong.demo.service.CrawlerUrlService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author DONG
* @description 针对表【crawler_url(爬虫url)】的数据库操作Service实现
* @createDate 2021-12-27 14:07:57
*/
@Service
public class CrawlerUrlServiceImpl extends ServiceImpl<CrawlerUrlMapper, CrawlerUrl>
    implements CrawlerUrlService{
    @Override
    public List<CrawlerUrl>  getByIdAndName(int id , String name ){
        return list(new QueryWrapper<CrawlerUrl>().eq("crawle_id", id).eq("crawle_name", name));
    }
    @Override
    public boolean saveOrUpdateByName(CrawlerUrl crawlerUrl){
        return newSaveOrUpdate(crawlerUrl, new UpdateWrapper<CrawlerUrl>()
                .eq("crawle_id", crawlerUrl.getCrawleId())
                .eq("crawle_name", crawlerUrl.getCrawleName())
                .eq("crawle_order", crawlerUrl.getCrawleOrder())
        );
    }

    /**
     * @param entity
     * @param updateWrapper
     * @return
     */
    boolean newSaveOrUpdate(CrawlerUrl entity, Wrapper<CrawlerUrl> updateWrapper) {
        return update(entity, updateWrapper) || save(entity);
    }

}




