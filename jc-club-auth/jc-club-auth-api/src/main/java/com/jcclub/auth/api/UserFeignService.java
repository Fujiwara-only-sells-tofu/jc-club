package com.jcclub.auth.api;

import com.jcclub.auth.entity.AuthUserDTO;
import com.jcclub.auth.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName：UserFeignService
 * @Author: gouteng
 * @Date: 2024/10/9 20:38
 * @Description: 用户服务feign客户端
 */

@FeignClient("jc-club-auth")
public interface UserFeignService {

    @PostMapping("/user/getUserInfo")
    Result<AuthUserDTO> getUserInfo(@RequestBody AuthUserDTO authUserDTO);
}
