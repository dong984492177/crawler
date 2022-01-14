package com.dong.demo;

import com.dong.demo.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis访问接口
 *
 * @author Dong_Jia_Qi on 2021/12/2
 */

@RestController
public class RedisController {
    @Autowired
    private RedisUtils redisUtils;
    @RequestMapping("setAndGet")
    public String test(String k,String v){
        redisUtils.set(k,v);
        return (String) redisUtils.get(k);
    }
}
