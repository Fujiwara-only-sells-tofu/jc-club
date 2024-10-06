package com.jcclub.oss.controller;

import com.jcclub.oss.config.StorageConfigProperties;
import com.jcclub.oss.entity.Result;
import com.jcclub.oss.service.FIleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName：FileController
 * @Author: gouteng
 * @Date: 2024/10/2 21:27
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FIleService fileService;

    @RequestMapping("/testGetAllBuckets")
    public String testGetAllBuckets() throws Exception {
        List<String> allBucket = fileService.getAllBucket();
        return allBucket.get(0);
    }

    @RequestMapping("/getUrl")
    public String getUrl(String bucketName, String objectName) throws Exception {
        return fileService.getUrl(bucketName, objectName);
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile uploadFile, String bucket, String objectName) throws Exception {
        String url = fileService.uploadFile(uploadFile, bucket, objectName);
        return Result.ok(url);
    }
}
