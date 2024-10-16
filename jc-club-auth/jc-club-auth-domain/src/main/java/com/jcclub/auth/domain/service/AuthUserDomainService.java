package com.jcclub.auth.domain.service;


import cn.dev33.satoken.stp.SaTokenInfo;
import com.jcclub.auth.domain.entity.AuthUserBO;

import java.util.List;

public interface AuthUserDomainService {


    /**
     * @Description: 用户注册
     * @data:[authUserBO]
     * @return: java.lang.Boolean
     * @Author: ZCY
     * @Date: 2024-10-04 14:50:39
     */

    Boolean register(AuthUserBO authUserBO);

    /**
     * @Description: 更新用户信息
     * @data:[authUserBO]
     * @return: java.lang.Boolean
     * @Author: ZCY
     * @Date: 2024-10-04 15:22:56
     */

    Boolean update(AuthUserBO authUserBO);

    /**
     * @Description: 删除用户信息
     * @data:[authUserBO]
     * @return: java.lang.Boolean
     * @Author: ZCY
     * @Date: 2024-10-04 15:36:17
     */

    Boolean delete(AuthUserBO authUserBO);

    /**
     * @Description: 用户登录
     * @data:[validCode]
     * @return: cn.dev33.satoken.stp.SaTokenInfo
     * @Author: ZCY
     * @Date: 2024-10-05 16:02:33
     */

    SaTokenInfo doLogin(String validCode);

    /**
     * @Description: 查询用户信息
     * @data:[authUserBO]
     * @return: java.lang.Boolean
     * @Author: ZCY
     * @Date: 2024-10-05 17:06:55
     */

    AuthUserBO  getUserInfo(AuthUserBO authUserBO);

    /**
    *@Title: listUserInfoByIds
    * @Author: 张辰逸
    * @Date: 2024-10-16 10:52:25
    * @Params: [userNameList]
    * @Return: List<AuthUserBO>
    * @Description: 根据id集合查询用户信息
     */

    List<AuthUserBO> listUserInfoByIds(List<String> userNameList);
}
