package com.geo.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassNameEgoItemService
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/16 23:02
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.geo.item.mapper")
public class EgoItemService {
    public static void main(String[] args) {
        SpringApplication.run(EgoItemService.class,args);
    }
}
