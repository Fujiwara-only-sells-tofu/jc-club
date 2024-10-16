package com.jcclub.circle.server.controller;

import com.jcclub.circle.api.common.ResultCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller启动问题
 *

 */
@RestController
@RequestMapping("/circle")
@Slf4j
@RequiredArgsConstructor
public class DemoController {

    @RequestMapping("/test")
    public String test() {
        return "test" + ResultCodeEnum.SUCCESS;
    }

}
