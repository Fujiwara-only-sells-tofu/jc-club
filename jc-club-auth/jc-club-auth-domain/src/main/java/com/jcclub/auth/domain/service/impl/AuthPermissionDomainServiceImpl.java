package com.jcclub.auth.domain.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcclub.auth.common.enums.IsDeletedFlagEnum;
import com.jcclub.auth.domain.convert.AuthPermissionBOConverter;
import com.jcclub.auth.domain.entity.AuthPermissionBO;
import com.jcclub.auth.domain.redis.RedisUtil;
import com.jcclub.auth.domain.service.AuthPermissionDomainService;
import com.jcclub.auth.infra.basic.entity.AuthPermission;
import com.jcclub.auth.infra.basic.entity.AuthRolePermission;
import com.jcclub.auth.infra.basic.service.IAuthPermissionService;
import com.jcclub.auth.infra.basic.service.IAuthRolePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthPermissionDomainServiceImpl implements AuthPermissionDomainService {


    private final IAuthPermissionService authPermissionService;

    private final IAuthRolePermissionService authRolePermissionService;


    private final RedisUtil redisUtil;

    private String authPermissionPrefix = "auth.permission";

    @Override
    public Boolean add(AuthPermissionBO authPermissionBO) {
        Integer count = authPermissionService.lambdaQuery()
                .eq(AuthPermission::getName, authPermissionBO.getName())
                .eq(AuthPermission::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if (count > 0) {
            log.error("权限名称已存在:{}", authPermissionBO.getName());
            throw new RuntimeException("权限名称已存在,不能重复添加！");
        }
        AuthPermission authPermission = AuthPermissionBOConverter.INSTANCE.convertBOToEntity(authPermissionBO);
        boolean result = authPermissionService.save(authPermission);
        return result;
    }

    @Override
    public Boolean update(AuthPermissionBO authPermissionBO) {
        AuthPermission authPermission = AuthPermissionBOConverter.INSTANCE.convertBOToEntity(authPermissionBO);
        boolean result = authPermissionService.updateById(authPermission);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(AuthPermissionBO authPermissionBO) {
        Integer count = authPermissionService.lambdaQuery()
                .eq(AuthPermission::getId, authPermissionBO.getId())
                .eq(AuthPermission::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(count <= 0){
            throw new RuntimeException("权限不存在,无法删除!");
        }
        List<AuthRolePermission> list = authRolePermissionService.lambdaQuery()
                .eq(AuthRolePermission::getPermissionId, authPermissionBO.getId())
                .eq(AuthRolePermission::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .list();
        if(CollUtil.isEmpty(list)){
            list.forEach(item -> {
                item.setPermissionId(null);
            });
        }
        AuthPermission authPermission = AuthPermissionBOConverter.INSTANCE.convertBOToEntity(authPermissionBO);
        authPermission.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = authPermissionService.updateById(authPermission);
        return result;
    }

    @Override
    public List<String> getPermission(String userName) {
        String permissionKey = redisUtil.buildKey(authPermissionPrefix, userName);
        String permissionValue = redisUtil.get(permissionKey);
        if (StringUtils.isBlank(permissionValue)) {
            return Collections.emptyList();
        }
        List<AuthPermission> permissionList = new Gson().fromJson(permissionValue,
                new TypeToken<List<AuthPermission>>() {
                }.getType());
        List<String> authList = permissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());
        if(CollUtil.isEmpty(authList)){
            throw new RuntimeException("用户没有权限");
        }
        return authList;
    }
}
