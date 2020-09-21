package com.ego.upload.service.impl;

import com.ego.upload.service.UploadService;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassNameUploadServiceImpl
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/20 23:49
 * @Version 1.0
 **/
@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    private List<String> imageSuffixList = Arrays.asList("jpg","png","img");
    @Override
    public String uploadImage(MultipartFile file) {
        String path=null;
        try {
            //图片校验
            //图片后缀是否正确
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀
            String substringAfterLast = StringUtils.substringAfterLast(originalFilename, ".");
            if(!imageSuffixList.contains(substringAfterLast)){
                return null;
            }
            //是不是图片内容
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(read==null){
                return null;
            }
            //保存图片到FastDFS服务器
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), substringAfterLast, null);
            //返回图片路径
            path = "http://image.ego.com/"+storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
