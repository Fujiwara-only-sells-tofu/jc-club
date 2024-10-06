package com.jcclub.auth.domain.service;


import cn.dev33.satoken.stp.SaTokenInfo;
import com.jcclub.auth.domain.entity.AuthUserBO;

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
}
