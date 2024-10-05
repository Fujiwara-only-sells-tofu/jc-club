package com.jcclub.auth.domain.service;


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
}
