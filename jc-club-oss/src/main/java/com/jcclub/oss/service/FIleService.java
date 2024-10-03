package com.jcclub.oss.service;

import com.jcclub.oss.adapter.StorageAdapter;
import org.springframework.stereotype.Service;

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
     * @Description: 查询所有bucket名字
     * @data:[bucketName]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-10-02 21:05:28
     */

    public List<String> getAllBucket(){
        return storageAdapter.getAllBucket();
    }



}
