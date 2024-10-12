package com.jcclub.subject.application.interceptor;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName：FeignConfiguration
 * @Author: gouteng
 * @Date: 2024/10/9 21:44
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Configuration
public class FeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
