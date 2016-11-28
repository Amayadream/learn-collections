package com.amayadream.quartz.model;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author :  Amayadream
 * @date :  2016.11.25 14:49
 */
@Component
public class MyBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Scheduled(cron="*/3 * * * * *")
    public void sayHello(){
        System.out.println("Hello, I'm " + this.name);
    }

}
