package com.ego.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassNameEgoUploadService
 * @Descripiotn 上传文件微服务启动类
 * @Author luokun
 * @Date 2020/9/21 10:53
 * @Version 1.0
 **/
@SpringBootApplication
public class EgoUploadService {
    public static void main(String[] args) {
        SpringApplication.run(EgoUploadService.class,args);
    }
}
