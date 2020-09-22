package com.ego.upload.service.impl;

import com.ego.upload.service.UploadService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassNameUploadServiceImpl
 * @Descripiotn 文件上传业务类
 * @Author luokun
 * @Date 2020/9/21 11:49
 * @Version 1.0
 **/
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    private static final List<String> imageSuffix = Arrays.asList("png","jpg","img");

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        //判断文件后缀是否正确
        String originalFilename = file.getOriginalFilename();
        String suffix = StringUtils.substringAfterLast(originalFilename, ".");
        if(!imageSuffix.contains(suffix)){
            log.debug("图片后缀错误：{}",suffix);
            return null;
        }
        //判断文件类容是否是图片
        BufferedImage read = ImageIO.read(file.getInputStream());
        if(read==null){
            log.debug("文件内容不是图片：{}",originalFilename);
            return null;
        }
        //上传图片至FastDFS
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), originalFilename, null);
        //获取图片路径
        String path="http://image.ego.com/"+storePath.getFullPath();
        return path;
    }
}
