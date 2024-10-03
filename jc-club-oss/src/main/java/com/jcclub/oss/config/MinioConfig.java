package com.jcclub.oss.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName：MinioConfig
 * @Author: gouteng
 * @Date: 2024/10/2 20:39
 * @Description: Minio配置管理
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfig {

    /**
     *minio地址
     */

    private String url;
    /**
     *minio账户
     */
    private String accessKey;
    /**
     *minio密码
     */
    private String secretKey;

    /**
     * @Description: 构造minio客户端
     * @data:[]
     * @return: io.minio.MinioClient
     * @Author: ZCY
     * @Date: 2024-10-02 20:51:27
     */

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }
}
