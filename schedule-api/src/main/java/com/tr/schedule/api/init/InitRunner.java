package com.tr.schedule.api.init;

import com.tr.schedule.biz.api.SchedulerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by yangjie on 2018/5/15.
 */
@Component
public class InitRunner implements CommandLineRunner {
    @Resource
    SchedulerService schedulerService;

    public void run(String... args) throws Exception {
        schedulerService.init();
    }

}
