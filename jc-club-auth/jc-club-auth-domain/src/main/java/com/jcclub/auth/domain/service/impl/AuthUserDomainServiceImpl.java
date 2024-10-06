package com.jcclub.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.jcclub.auth.common.enums.AuthUserStatusEnum;
import com.jcclub.auth.common.enums.IsDeletedFlagEnum;
import com.jcclub.auth.domain.constants.AuthConstant;
import com.jcclub.auth.domain.convert.AuthUserBOConverter;
import com.jcclub.auth.domain.entity.AuthUserBO;
import com.jcclub.auth.domain.redis.RedisUtil;
import com.jcclub.auth.domain.service.AuthUserDomainService;
import com.jcclub.auth.infra.basic.entity.*;
import com.jcclub.auth.infra.basic.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName：AuthUserDomainServiceImpl
 * @Author: gouteng
 * @Date: 2024/10/4 14:40
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    private final IAuthUserService authUserService;

    private final IAuthUserRoleService authUserRoleService;

    private final IAuthRoleService authRoleService;

    private final IAuthPermissionService authPermissionService;

    private final IAuthRolePermissionService authRolePermissionService;

    private final RedisUtil redisUtil;

    private static final String LOGIN_PREFIX = "loginCode";

    private String authPermissionPrefix = "auth.permission";

    private String authRolePrefix = "auth.role";

    private String salt = "jcclub";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        Integer count = authUserService.lambdaQuery().eq(AuthUser::getPhone, authUserBO.getPhone()).count();
        if (count > 0) {
            throw new RuntimeException("手机号码已存在");
        }
        AuthUser authUser = AuthUserBOConverter.INSTANCE.authUserBOtoEntity(authUserBO);
        //校验用户是否存在
        Integer existCount = authUserService.lambdaQuery().eq(AuthUser::getUserName, authUser.getUserName()).count();
        if (existCount > 0) {
            return true;
        }
        if(StrUtil.isNotBlank(authUser.getPassword())){
            authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));
        }
        if (StringUtils.isBlank(authUser.getAvatar())) {
            authUser.setAvatar("http://192.168.174.100:9000/user/icon");
        }
        if (StringUtils.isBlank(authUser.getNickName())) {
            authUser.setNickName("鸡翅粉丝");
        }
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        boolean result = authUserService.save(authUser);
        //建立一个初步的角色的关联
        AuthRole role = authRoleService.lambdaQuery().eq(AuthRole::getRoleKey, AuthConstant.NORMAL_USER).one();
        Long roleId = role.getId();
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setRoleId(roleId);
        authUserRole.setUserId(authUser.getId());
        authUserRoleService.save(authUserRole);
        //要把当前用户的角色和权限保存到redis中
        String roleKey = redisUtil.buildKey(authRolePrefix, authUser.getUserName());
        LinkedList<AuthRole> roleList = new LinkedList<>();
        roleList.add(role);
        redisUtil.set(roleKey, new Gson().toJson(roleList));

        List<AuthRolePermission> rolePermissionList = authRolePermissionService.lambdaQuery()
                .eq(AuthRolePermission::getRoleId, roleId).list();
        List<Long> permissionIdList = rolePermissionList.stream()
                .map(AuthRolePermission::getPermissionId)
                .collect(Collectors.toList());
        //根据roleId查权限
        List<AuthPermission> perimissionList = authPermissionService.lambdaQuery().in(AuthPermission::getId, permissionIdList).list();
        String permissionKey = redisUtil.buildKey(authPermissionPrefix, authUser.getUserName());
        redisUtil.set(permissionKey, new Gson().toJson(perimissionList));


        return result;
    }


    @Override
    public Boolean update(AuthUserBO authUserBO) {
        //先查出来实体判断状态
        AuthUser user = authUserService.getById(authUserBO.getId());
        if (user.getStatus() == AuthUserStatusEnum.CLOSE.getCode()) {
            log.error("用户状态不正确");
            throw new RuntimeException("用户状态为禁用，不能修改！");
        }
        AuthUser authUser = AuthUserBOConverter.INSTANCE.authUserBOtoEntity(authUserBO);
        boolean result = authUserService.updateById(authUser);
        //TODO 有任何更新要和缓存进行同步
        return result;
    }


    @Override
    public Boolean delete(AuthUserBO authUserBO) {
        AuthUser user = authUserService.lambdaQuery()
                .eq(AuthUser::getId, authUserBO.getId())
                .eq(AuthUser::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .one();
        if(user == null){
            throw new RuntimeException("用户不存在,不能删除！");
        }
        AuthUser authUser = AuthUserBOConverter.INSTANCE.authUserBOtoEntity(authUserBO);
        authUser.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        //删除用户和角色关联
        AuthUserRole one = authUserRoleService.lambdaQuery()
                .eq(AuthUserRole::getUserId, authUser.getId())
                .eq(AuthUserRole::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .one();
        if (one != null) {
            one.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
            authUserRoleService.updateById(one);
        }
        boolean result = authUserService.updateById(authUser);
        return result;
    }


    @Override
    public SaTokenInfo doLogin(String validCode) {
        //构建rediskey
        String loginKey = redisUtil.buildKey(LOGIN_PREFIX, validCode);
        String openId = redisUtil.get(loginKey);
        if (StrUtil.isBlank(openId)) {
            return null;
        }
        AuthUserBO authUserBO = new AuthUserBO();
        authUserBO.setUserName(openId);
        this.register(authUserBO);
        StpUtil.login(openId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return tokenInfo;
    }

    @Override
    public AuthUserBO getUserInfo(AuthUserBO authUserBO) {
        AuthUser user = authUserService.lambdaQuery().eq(AuthUser::getUserName, authUserBO.getUserName()).one();
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        AuthUserBO bo = AuthUserBOConverter.INSTANCE.authUsertoBO(user);
        return bo;
    }
}
