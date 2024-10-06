package com.jcclub.oss.service;

import com.jcclub.oss.adapter.StorageAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName：FIleService
 * @Author: gouteng
 * @Date: 2024/10/2 22:26
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Service
public class FIleService {

    private final StorageAdapter storageAdapter;

    public FIleService(StorageAdapter storageAdapter) {
        this.storageAdapter = storageAdapter;
    }


    /**
     * 列出所有桶
     */
    public List<String> getAllBucket() {
        return storageAdapter.getAllBucket();
    }

    /**
     * 获取文件路径
     */
    public String getUrl(String bucketName,String objectName) {
        return storageAdapter.getUrl(bucketName,objectName);
    }


    public String uploadFile(MultipartFile uploadFile, String bucket, String objectName){
        storageAdapter.uploadFile(uploadFile,bucket,objectName);
        objectName = objectName + "/" + uploadFile.getOriginalFilename();
        return storageAdapter.getUrl(bucket, objectName);
    }
}
