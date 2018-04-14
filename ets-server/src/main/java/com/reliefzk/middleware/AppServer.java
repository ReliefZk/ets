package com.reliefzk.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource(locations={"classpath:application-context.xml", "classpath*:ets-scheduler.xml"})
public class AppServer {
    public static void main(String[] args) {
        SpringApplication.run(AppServer.class, args);
    }
}
