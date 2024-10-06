package com.jcclub.auth.domain.service.impl;


import com.jcclub.auth.common.enums.IsDeletedFlagEnum;
import com.jcclub.auth.domain.convert.AuthRoleBOConverter;
import com.jcclub.auth.domain.entity.AuthRoleBO;
import com.jcclub.auth.domain.service.AuthRoleDomainService;
import com.jcclub.auth.infra.basic.entity.AuthRole;
import com.jcclub.auth.infra.basic.entity.AuthUserRole;
import com.jcclub.auth.infra.basic.service.IAuthRoleService;
import com.jcclub.auth.infra.basic.service.IAuthUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthRoleDomainServiceImpl implements AuthRoleDomainService {

    private final IAuthRoleService authRoleService;

    private final IAuthUserRoleService authUserRoleService;

    @Override
    public Boolean add(AuthRoleBO authRoleBO) {
        Integer count = authRoleService.lambdaQuery()
                .eq(AuthRole::getRoleName, authRoleBO.getRoleName())
                .eq(AuthRole::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(count > 0){
            throw new RuntimeException("角色已存在,不能重复添加！");
        }
        AuthRole authRole = AuthRoleBOConverter.INSTANCE.convertBOToEntity(authRoleBO);
        boolean result = authRoleService.save(authRole);
        return result;
    }

    @Override
    public Boolean update(AuthRoleBO authRoleBO) {
        AuthRole authRole = AuthRoleBOConverter.INSTANCE.convertBOToEntity(authRoleBO);
        boolean result = authRoleService.updateById(authRole);
        return result;
    }

    @Override
    public Boolean delete(AuthRoleBO authRoleBO) {
        Integer count = authRoleService.lambdaQuery()
                .eq(AuthRole::getId, authRoleBO.getId())
                .eq(AuthRole::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(count <= 0){
            throw new RuntimeException("角色不存在,不能删除!");
        }
        Integer userRoleCount = authUserRoleService.lambdaQuery()
                .eq(AuthUserRole::getRoleId, authRoleBO.getId())
                .eq(AuthUserRole::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(userRoleCount > 0){
            throw new RuntimeException("该角色已被用户关联,请先修改用户权限，再删除!");

        }
        AuthRole authRole = AuthRoleBOConverter.INSTANCE.convertBOToEntity(authRoleBO);
        authRole.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = authRoleService.updateById(authRole);
        return result;
    }
}
