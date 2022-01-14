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
}
