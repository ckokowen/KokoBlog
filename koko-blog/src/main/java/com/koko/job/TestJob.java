package com.koko.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-08-20:37
 */
@Component
public class TestJob {

//    @Scheduled(cron = "0/5 * * * * ?")
    public void testJob(){
        //要定时执行的代码
        System.out.println("定时任务执行了");
    }

}
