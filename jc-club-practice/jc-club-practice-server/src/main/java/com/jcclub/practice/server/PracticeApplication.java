package com.jcclub.practice.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 练习微服务启动类
 *
 * @author: ChickenWing
 * @date: 2024/3/2
 */
@SpringBootApplication
@ComponentScan("com.jcclub")
@MapperScan("com.jcclub.**.mapper")
@EnableFeignClients(basePackages = "com.jcclub")
public class PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class);
    }

}
