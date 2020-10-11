package com.ego.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName EgoSearchService
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/26 22:21
 * @Version 1.0
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
public class EgoSearchService {
    public static void main(String[] args) {
        SpringApplication.run(EgoSearchService.class,args);
    }
}
