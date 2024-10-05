package com.jcclub.auth.domain.service.impl;


import com.google.common.reflect.TypeToken;
import com.jcclub.auth.common.enums.IsDeletedFlagEnum;
import com.jcclub.auth.domain.convert.AuthPermissionBOConverter;
import com.jcclub.auth.domain.entity.AuthPermissionBO;
import com.jcclub.auth.domain.service.AuthPermissionDomainService;
import com.jcclub.auth.infra.basic.entity.AuthPermission;
import com.jcclub.auth.infra.basic.service.IAuthPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthPermissionDomainServiceImpl implements AuthPermissionDomainService {


    private final IAuthPermissionService authPermissionService;

    @Override
    public Boolean add(AuthPermissionBO authPermissionBO) {
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
    public Boolean delete(AuthPermissionBO authPermissionBO) {

        AuthPermission authPermission = AuthPermissionBOConverter.INSTANCE.convertBOToEntity(authPermissionBO);
        authPermission.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = authPermissionService.updateById(authPermission);
        return result;
    }

    @Override
    public List<String> getPermission(String userName) {
        return Collections.emptyList();
    }
}
