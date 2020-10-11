package com.ego.goodsweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName EgoGoodsWebService
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/10/9 0:57
 * @Version 1.0
 **/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
public class EgoGoodsWebService {
    public static void main(String[] args) {
        SpringApplication.run(EgoGoodsWebService.class, args);
    }
}
