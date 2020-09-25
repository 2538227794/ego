package com.ego.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassNameEgoRegistry
 * @Descripiotn
 * @Author luokun
 * @Date 2020/9/16 22:17
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class EgoRegistry {

    public static void main(String[] args) {
        SpringApplication.run(EgoRegistry.class,args);
    }
}
