package com.jcclub.circle.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 圈子微服务启动类
 *

 */
@SpringBootApplication
@ComponentScan("com.jcclub")
@MapperScan("com.jcclub.**.mapper")
@EnableFeignClients(basePackages = "com.jcclub")
public class CircleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircleApplication.class);
    }

}
