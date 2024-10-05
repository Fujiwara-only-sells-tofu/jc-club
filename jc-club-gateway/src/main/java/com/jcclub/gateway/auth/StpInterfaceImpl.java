package com.jcclub.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.google.gson.Gson;
import com.jcclub.gateway.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 自定义权限验证接口扩展 
 */
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {


    private final RedisUtil redisUtil;

    private String authPermissionPrefix = "auth.permission";

    private String authRolePrefix = "auth.role";

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限列表
        //1.直接跟数据库进行交互
        //2.通过redis缓存
        //3.redis缓存，没有的话，去调用我们的微服务去获取
        return getAuth(loginId.toString(), authPermissionPrefix);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的角色列表
        return getAuth(loginId.toString(), authRolePrefix);
    }

    private List<String> getAuth(String loginId, String prefix){
        String authKey = redisUtil.buildKey(prefix, loginId.toString());
        String authValue = redisUtil.get(authKey);
        if(StringUtils.isBlank(authValue)){
            return Collections.emptyList();
        }
        List<String> authList = new Gson().fromJson(authValue, List.class);
        return authList;
    }

}
