package com.ego.upload.controller;

import com.ego.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassNameBrandController
 * @Descripiotn 文件上传控制层
 * @Author luokun
 * @Date 2020/9/21 11:46
 * @Version 1.0
 **/
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {
    @Autowired
    private UploadService uploadService;

    /**
     *
     * @Author luokun
     * @Description 上传图片
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    @RequestMapping("/image")
    public ResponseEntity<String> image(@RequestParam("file")MultipartFile file){
        try {
            String path=uploadService.uploadImage(file);
            if(StringUtils.isBlank(path)){
                //响应404
                log.debug("没有找到文件路径",path);
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(path);
        } catch (IOException e) {
            log.error("图片上传出现异常：",e);
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }


}
