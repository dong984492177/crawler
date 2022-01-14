package com.dong.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.demo.model.TutorialsNode;

/**
* @author DONG
* @description 针对表【tutorials_node(爬虫一些教程 需要存放的路径信息)】的数据库操作Service
* @createDate 2021-12-19 03:23:05
*/
public interface TutorialsNodeService extends IService<TutorialsNode> {
    /**
     * 通过 crawle_id 和 name 来获得指定数据,如果redis有数据拿redis的
     * @param id 项目的id (crawle_id) 非 数据的id
     * @param name 节点教程名字
     * @return
     */
    TutorialsNode getByName(int id ,String name);

    boolean saveOrUpdateByName(TutorialsNode tutorialsNode);
    /**
     * 通过 crawle_id 和 name 来查数据库获得指定数据,并刷新 redis
     * @param id 项目的id (crawle_id) 非 数据的id
     * @param name 节点教程名字
     * @return
     */
    public TutorialsNode getDbByName(int id, String name);

}
