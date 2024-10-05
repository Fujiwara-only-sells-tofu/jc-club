package com.jcclub.auth.domain.service.impl;


import com.jcclub.auth.common.enums.IsDeletedFlagEnum;
import com.jcclub.auth.domain.convert.AuthRoleBOConverter;
import com.jcclub.auth.domain.entity.AuthRoleBO;
import com.jcclub.auth.domain.service.AuthRoleDomainService;
import com.jcclub.auth.infra.basic.entity.AuthRole;
import com.jcclub.auth.infra.basic.service.IAuthRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthRoleDomainServiceImpl implements AuthRoleDomainService {

    private final IAuthRoleService authRoleService;

    @Override
    public Boolean add(AuthRoleBO authRoleBO) {
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
        AuthRole authRole = AuthRoleBOConverter.INSTANCE.convertBOToEntity(authRoleBO);
        authRole.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = authRoleService.updateById(authRole);
        return result;
    }
}
