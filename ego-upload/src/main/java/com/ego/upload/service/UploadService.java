package com.ego.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassNameUploadService
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/20 23:48
 * @Version 1.0
 **/
public interface UploadService {
    /**
     *
     * @Author luokun
     * @Description 图片上传
     * @Date 2020/7/8 10:05
     * @param null
     * @return
     **/
    String uploadImage(MultipartFile file);
}
