package com.jcclub.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName：StorageConfigProperties
 * @Author: gouteng
 * @Date: 2024/10/3 14:06
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@ConfigurationProperties(prefix = "storage.service")
@Component
@Data
@RefreshScope
public class StorageConfigProperties {

    private String type;
}
