package com.dong.demo.service;

import com.dong.demo.model.TutorialsMapping;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author DONG
* @description 针对表【tutorials_mapping(教程爬虫 映射类)】的数据库操作Service
* @createDate 2021-12-27 14:47:28
*/
public interface TutorialsMappingService extends IService<TutorialsMapping> {
    TutorialsMapping getById(int id);
}
