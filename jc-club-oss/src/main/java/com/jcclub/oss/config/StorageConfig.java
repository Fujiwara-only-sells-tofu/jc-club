package com.jcclub.oss.config;


import com.jcclub.oss.adapter.StorageAdapter;
import com.jcclub.oss.adapter.ALiStorageAdapter;
import com.jcclub.oss.adapter.MinioStorageAdapter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 文件存储config
 *
 * @author: ChickenWing
 * @date: 2023/10/14
 */
@Configuration
@RefreshScope
@EnableAutoConfiguration
public class StorageConfig {


    @Resource
    private StorageConfigProperties storage;



    @Bean
    @RefreshScope
    public StorageAdapter storageService() {
        if ("minio".equals(storage.getType())) {
            return new MinioStorageAdapter();
        } else if ("aliyun".equals(storage.getType())) {
            return new ALiStorageAdapter();
        } else {
            throw new IllegalArgumentException("未找到对应的文件存储处理器");
        }
    }

}
