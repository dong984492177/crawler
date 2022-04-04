package com.dong.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 跳转页面
 *
 * @author Dong_Jia_Qi on 2022/1/26
 */
@Controller
public class ToHtmlController {
    @GetMapping("crawlerIndex")//页面的url地址
    public String getindex(Model model)//对应函数
    {
        // model.addAttribute("name","bigsai");
        return "crawlerIndex";//与templates中index.html对应
    }
}
