package com.ego.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassNameEgoItemService
 * @Descripiotn 商品微服务启动类
 * @Author luokun
 * @Date 2020/9/21 10:25
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan("com.ego.item.mapper")
public class EgoItemService {
    public static void main(String[] args) {
        SpringApplication.run(EgoItemService.class,args);
    }
}
