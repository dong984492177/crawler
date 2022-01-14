package com.dong.demo.mapper;

import com.dong.demo.model.TutorialsNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DONG
* @description 针对表【tutorials_node(爬虫一些教程 需要存放的路径信息)】的数据库操作Mapper
* @createDate 2021-12-19 03:23:04
* @Entity com.dong.demo.model.TutorialsNode
*/
@Mapper
public interface TutorialsNodeMapper extends BaseMapper<TutorialsNode> {

}




