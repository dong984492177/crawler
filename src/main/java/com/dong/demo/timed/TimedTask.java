package com.dong.demo.timed;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务类
 *
 * @author Dong_Jia_Qi on 2022/1/25
 */
@Component
public class TimedTask {
    // @Scheduled(initialDelay = 30000, fixedRate = 10*1000*60)
    public void scheduledTask() {
        System.out.println("任务执行时间：" + LocalDateTime.now());
    }
}
