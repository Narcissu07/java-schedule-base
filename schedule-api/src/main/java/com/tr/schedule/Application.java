package com.tr.schedule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by 7 on 2017/08/14.
 */
@SpringBootApplication(scanBasePackages = "com.tr")
@MapperScan(basePackages = "com.tr.schedule.dal.mybatis.mapper")
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
