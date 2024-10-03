package com.jcclub.oss.adapter;

import com.jcclub.oss.entity.FIleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName：MinioStorageServiceImpl
 * @Author: gouteng
 * @Date: 2024/10/2 21:54
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@RequiredArgsConstructor
public class ALiStorageAdapter implements StorageAdapter {
    @Override
    public void createBucket(String bucketName) {
        
    }

    @Override
    public void uploadFile(MultipartFile uploadFile, String bucket, String objectName) {

    }

    @Override
    public List<String> getAllBucket() {
        List<String> bucketNameList = new LinkedList<>();
        bucketNameList.add("aliyun");
        return bucketNameList;
    }

    @Override
    public List<FIleInfo> getAllFile(String bucket) {
        return Collections.emptyList();
    }

    @Override
    public InputStream downLoad(String bucket, String objectName) {
        return null;
    }

    @Override
    public void deleteBucket(String bucket) {

    }

    @Override
    public void deleteObject(String bucket, String objectName) {

    }
}
