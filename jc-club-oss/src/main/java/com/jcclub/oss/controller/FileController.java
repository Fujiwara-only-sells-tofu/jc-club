package com.jcclub.oss.controller;

import cn.hutool.core.collection.CollUtil;
import com.jcclub.oss.config.StorageConfigProperties;
import com.jcclub.oss.service.FIleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName：FileController
 * @Author: gouteng
 * @Date: 2024/10/2 21:27
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@RestController
@RequestMapping("/file")
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FIleService fIleService;


    private final StorageConfigProperties storage;
    @GetMapping("/test")
    public String test() throws Exception {
        List<String> allBucket = fIleService.getAllBucket();
        if(CollUtil.isEmpty(allBucket)){
            return "no bucket";

        }
        return "bucket:" + allBucket.toString();
    }


    @GetMapping("/testNacos")
    public String testNacos(){

        return storage.getType();
    }
}
