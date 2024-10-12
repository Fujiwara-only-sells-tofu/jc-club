package com.jcclub.auth.application.controller.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.jcclub.auth.application.controller.convert.AuthUserDTOConverter;
import com.jcclub.auth.entity.AuthUserDTO;
import com.jcclub.auth.entity.Result;
import com.jcclub.auth.domain.entity.AuthUserBO;
import com.jcclub.auth.domain.service.AuthUserDomainService;
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
public class UserController {

    private final AuthUserDomainService authUserDomainService;


    /**
     * @Description: 注册用户
     * @data:[authUserDTO]
     * @return: com.jcclub.auth.entity.Result<java.lang.Boolean>
     * @Author: ZCY
     * @Date: 2024-10-04 14:17:33
     */

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody AuthUserDTO authUserDTO){
        log.info("注册用户,参数为{}",authUserDTO);
        try {
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getUserName()), "用户名称不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getEmail()), "邮件地址不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getPassword()), "密码不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getPhone()), "手机号不能为空");


            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.authUserDTOtoBO(authUserDTO);
            Boolean result= authUserDomainService.register(authUserBO);
            return Result.ok(result);

        }catch (Exception e){
            log.error("注册用户失败,异常信息为{}",e);
            return Result.fail(e.getMessage());
        }

    }
    /**
     * @Description: 更新用户信息
     * @data:[authUserDTO]
     * @return: com.jcclub.auth.entity.Result<java.lang.Boolean>
     * @Author: ZCY
     * @Date: 2024-10-04 15:22:42
     */

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody AuthUserDTO authUserDTO){
        log.info("更新用户信息,参数为{}",authUserDTO);
        try {
            Preconditions.checkArgument(authUserDTO.getUserName() != null, "用户名称不能为空");

            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.authUserDTOtoBO(authUserDTO);
            Boolean result= authUserDomainService.update(authUserBO);
            return Result.ok(result);

        }catch (Exception e){
            log.error("更新用户信息失败,异常信息为{}",e);
            return Result.fail(e.getMessage());
        }

    }
    @PostMapping("/getUserInfo")
    public Result<AuthUserDTO> getUserInfo(@RequestBody AuthUserDTO authUserDTO){
        log.info("查询用户信息,参数为{}",authUserDTO);
        try {
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getUserName()), "用户名称不能为空");
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.authUserDTOtoBO(authUserDTO);
            AuthUserBO bo = authUserDomainService.getUserInfo(authUserBO);
            AuthUserDTO dto = AuthUserDTOConverter.INSTANCE.authUserBOtoDTO(bo);
            return Result.ok(dto);

        }catch (Exception e){
            log.error("查询用户信息失败,异常信息为{}",e);
            return Result.fail(e.getMessage());
        }

    }


    /**
     * 用户退出
     */
    @RequestMapping("logOut")
    public Result logOut(@RequestParam String userName) {
        try {
            log.info("UserController.logOut.userName:{}", userName);
            Preconditions.checkArgument(!StringUtils.isBlank(userName), "用户名不能为空");
            StpUtil.logout(userName);
            return Result.ok();
        } catch (Exception e) {
            log.error("UserController.logOut.error:{}", e.getMessage(), e);
            return Result.fail("用户登出失败");
        }
    }

    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody AuthUserDTO authUserDTO){
        log.info("删除用户信息,参数为{}",authUserDTO);
        try {
            Preconditions.checkArgument(authUserDTO.getId() != null, "用户id不能为空");


            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.authUserDTOtoBO(authUserDTO);
            Boolean result= authUserDomainService.delete(authUserBO);
            return Result.ok(result);

        }catch (Exception e){
            log.error("删除用户失败,异常信息为{}",e);
            return Result.fail(e.getMessage());
        }

    }







    @RequestMapping("doLogin")
    public Result<SaTokenInfo> doLogin(@RequestParam("validCode") String validCode) {
        try {
            Preconditions.checkArgument(!StringUtils.isBlank(validCode), "验证码不能为空");
            SaTokenInfo tokenInfo = authUserDomainService.doLogin(validCode);
            return Result.ok(tokenInfo);
        }catch (Exception e){
            log.error("登录失败,异常信息为{}",e);
            return Result.fail(e.getMessage());
        }

    }

        // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}

