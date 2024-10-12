package com.jcclub.subject.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName：ThreadPoolConfig
 * @Author: gouteng
 * @Date: 2024/10/9 14:30
 * @Description: 线程池的config管理
 */

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "labelThreadPool")
    public ThreadPoolExecutor getLabelThreadPool(){
        return new ThreadPoolExecutor
                (20,100,5,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(40),
                        new CustomNameThreadFactory("label"),
                        new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
