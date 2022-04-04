package com.dong.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.demo.mapper.TutorialsMappingMapper;
import com.dong.demo.model.TutorialsMapping;
import com.dong.demo.service.TutorialsMappingService;
import com.dong.demo.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author DONG
* @description 针对表【tutorials_mapping(教程爬虫 映射类)】的数据库操作Service实现
* @createDate 2021-12-19 19:05:51
*/
@Service
@Slf4j
public class TutorialsMappingServiceImpl extends ServiceImpl<TutorialsMappingMapper, TutorialsMapping>
    implements TutorialsMappingService{
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    protected TutorialsMappingMapper tutorialsMappingMapper;
    /**
     * redis 获得一条数据
     * @param id 表id
     * @return
     */
    @Override
    public TutorialsMapping getById(int id) {
        TutorialsMapping tutorialsMapping;
        String key = getTutorialsMappingKey(id);
        String tutorialsMappingStringJson = (String) redisUtils.get(key);
        if (tutorialsMappingStringJson == null) {
            tutorialsMapping = tutorialsMappingMapper.selectById(id);
            if (tutorialsMapping == null) {
                log.info("tutorialsMapping 没有数据 ");
                return null;
            }else{
                redisUtils.set(key, JSON.toJSONString(tutorialsMapping));
            }
        }else {
            tutorialsMapping = JSON.parseObject(tutorialsMappingStringJson,TutorialsMapping.class);
        }
        if (tutorialsMapping == null) {
            log.info("tutorialsMapping 没有数据 ");
            return null;
        }
        return tutorialsMapping;
    }

    private String getTutorialsMappingKey(int id) {
        String key = "tutorialsMapping"+ id;
        return key;
    }

    /**
     * 拿到
     * @return
     */
    @Override
    public List<TutorialsMapping> getByType(int type){
        List<TutorialsMapping> list = list(new QueryWrapper<TutorialsMapping>().eq("crawlerType",type));
        for (TutorialsMapping tutorialsMapping : list) {
            Integer id = tutorialsMapping.getId();
            String key = getTutorialsMappingKey(id);
            String tutorialsMappingStringJson = (String) redisUtils.get(key);
            if (tutorialsMappingStringJson == null) {
                redisUtils.set(key, JSON.toJSONString(tutorialsMapping));
            }
        }
        return list;
    }

}




