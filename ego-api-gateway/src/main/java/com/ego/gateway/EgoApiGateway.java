package com.ego.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ClassNameEgoGateway
 * @Descripiotn
 * @Author luokun
 * @Date 2020/9/16 22:28
 * @Version 1.0
 **/
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class EgoApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(EgoApiGateway.class,args);
    }
}
