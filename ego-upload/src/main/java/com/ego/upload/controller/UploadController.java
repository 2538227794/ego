package com.ego.upload.controller;

import com.ego.upload.EgoUploadService;
import com.ego.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassNameUploadController
 * @Descripiotn 图片文件上传
 * @Author luokun
 * @Date 2020/9/20 23:46
 * @Version 1.0
 **/
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    public ResponseEntity<String> image(@RequestParam("file")MultipartFile file){
        String path=uploadService.uploadImage(file);
        if(StringUtils.isBlank(path)){
            //响应400
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(path);
    }
}
