package com.dong.demo.service;

/**
 * 教程爬虫接口
 *
 * @author Dong_Jia_Qi on 2021/12/19
 */

public interface TutorialsService {
    /**
     * 开始爬虫
     * @param id
     * @param name
     * @return
     */
    public boolean crawler(int id ,String name);

    /**
     * 去爬网站的节点,一个网站有很多教程
     * @param id 项目对应的id
     * @param appendStr 追加条件,部分会用到
     * @return
     */
    public boolean crawlerNode(int id, String appendStr);



}
