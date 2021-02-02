package com.yellowzero.backend;

import cn.hutool.cron.CronUtil;
import com.yellowzero.backend.util.ImageTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        CronUtil.schedule("* */1 * * *", new ImageTask());
        CronUtil.start();
    }
}
