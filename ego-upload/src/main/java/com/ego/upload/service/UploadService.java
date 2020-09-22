package com.ego.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassNameUploadService
 * @Descripiotn 文件上传业务接口
 * @Author luokun
 * @Date 2020/9/21 11:48
 * @Version 1.0
 **/
public interface UploadService {
    /**
     *
     * @Author luokun
     * @Description 上传图片接口
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    String uploadImage(MultipartFile file) throws IOException;
}
