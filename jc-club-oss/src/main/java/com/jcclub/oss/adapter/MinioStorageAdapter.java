package com.jcclub.oss.adapter;

import com.jcclub.oss.config.MinioConfig;
import com.jcclub.oss.entity.FIleInfo;
import com.jcclub.oss.utils.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName：MinioStorageServiceImpl
 * @Author: gouteng
 * @Date: 2024/10/2 21:54
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

public class MinioStorageAdapter implements StorageAdapter {


    @Resource
    private  MinioUtil minioUtil;

    @Resource
    private  MinioConfig minioConfig;



    @Override
    @SneakyThrows //在编译的时候抛出异常
    public void createBucket(String bucket) {
        minioUtil.createBucket(bucket);
    }

    @Override
    @SneakyThrows
    public void uploadFile(MultipartFile uploadFile, String bucket, String objectName) {
        minioUtil.createBucket(bucket);
        if(objectName != null){
            minioUtil.uploadFile(uploadFile.getInputStream(),bucket,objectName+"/"+uploadFile.getName());
        }else {
            minioUtil.uploadFile(uploadFile.getInputStream(),bucket,uploadFile.getName());
        }
    }

    @Override
    @SneakyThrows
    public List<String> getAllBucket() {
        return minioUtil.getAllBucket();
    }

    @Override
    @SneakyThrows
    public List<FIleInfo> getAllFile(String bucket) {
        return minioUtil.getAllFile(bucket);
    }

    @Override
    @SneakyThrows
    public InputStream downLoad(String bucket, String objectName) {
        return minioUtil.downLoad(bucket,objectName);
    }

    @Override
    @SneakyThrows
    public void deleteBucket(String bucket) {
        minioUtil.deleteBucket(bucket);
    }

    @Override
    @SneakyThrows
    public void deleteObject(String bucket, String objectName) {
        minioUtil.deleteObject(bucket,objectName);
    }

    @Override
    @SneakyThrows
    public String getUrl(String bucket, String objectName) {
        return minioConfig.getUrl() + "/" + bucket + "/" + objectName;
    }

}
