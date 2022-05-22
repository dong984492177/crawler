package com.dong.demo.mapper;

import com.dong.demo.model.TutorialsMapping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DONG
 * @description 针对表【tutorials_mapping(教程爬虫 映射类)】的数据库操作Mapper
 * @createDate 2021-12-27 14:47:27
 * @Entity com.dong.demo.model.TutorialsMapping
 */
@Mapper
public interface TutorialsMappingMapper extends BaseMapper<TutorialsMapping> {

}




