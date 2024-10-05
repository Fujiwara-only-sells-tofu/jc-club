package com.jcclub.auth.domain.service.impl;


import com.jcclub.auth.common.enums.IsDeletedFlagEnum;
import com.jcclub.auth.domain.entity.AuthRolePermissionBO;
import com.jcclub.auth.domain.service.AuthRolePermissionDomainService;
import com.jcclub.auth.infra.basic.entity.AuthRolePermission;
import com.jcclub.auth.infra.basic.service.IAuthRolePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthRolePermissionDomainServiceImpl implements AuthRolePermissionDomainService {


    private final IAuthRolePermissionService authRolePermissionService;

    @Override
    public Boolean add(AuthRolePermissionBO authRolePermissionBO) {

        List<AuthRolePermission> rolePermissionList = new LinkedList<>();
        Long roleId = authRolePermissionBO.getRoleId();
        authRolePermissionBO.getPermissionIdList().forEach(permissionId -> {
            AuthRolePermission authRolePermission = new AuthRolePermission();
            authRolePermission.setRoleId(roleId);
            authRolePermission.setPermissionId(permissionId);
            rolePermissionList.add(authRolePermission);
        });
        boolean result = authRolePermissionService.saveBatch(rolePermissionList);
        return result;
    }
}
