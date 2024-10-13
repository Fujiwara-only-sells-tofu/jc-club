package com.jcclub.practice.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller启动问题
 *

 */
@RestController
@RequestMapping("/practice")
@Slf4j
public class DemoController {

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

}
