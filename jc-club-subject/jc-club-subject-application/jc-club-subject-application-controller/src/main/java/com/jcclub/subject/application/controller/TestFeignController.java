package com.jcclub.subject.application.controller;


import com.google.common.base.Preconditions;
import com.jcclub.auth.entity.AuthUserDTO;
import com.jcclub.auth.entity.Result;
import com.jcclub.subject.common.entity.PageResult;
import com.jcclub.subject.infra.basic.entity.SubjectInfoEs;
import com.jcclub.subject.infra.basic.service.SubjectEsService;
import com.jcclub.subject.infra.entity.UserInfo;
import com.jcclub.subject.infra.rpc.UserRpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName：UserController
 * @Author: gouteng
 * @Date: 2024/10/3 17:22
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class TestFeignController {

    private final UserRpc userRpc;

    @GetMapping("/testFeign")
    public void testFeign(){
        UserInfo user = userRpc.getUserInfo("onfRd6w2vhMb7JgyKVAm75lWde7s");
        log.info("user:{}",user);
    }



}

