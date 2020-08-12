package com.cxu.web.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    public MySpringApplicationRunListener(SpringApplication application, String[] args){

    }
    @Override
    public void starting() {
        System.out.printf("SpringApplicationRunListener....strart");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
        systemEnvironment.forEach((k,v)-> {
            System.out.printf("k-----"+k+"------v"+v);
        });
        Object o = environment.getSystemProperties().get("os.name");
        System.out.printf("系统属性："+o.toString());
        System.out.printf("environmentPrepared。。。。");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

        System.out.printf("--------contextPrepared---------");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

        System.out.printf("--------contextPrepared---------");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {

        System.out.printf("--------started---------");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {

        System.out.printf("--------running---------");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

        System.out.printf("--------failed---------");
    }
}
