package com.jcclub.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.jcclub.auth.common.enums.AuthUserStatusEnum;
import com.jcclub.auth.common.enums.IsDeletedFlagEnum;
import com.jcclub.auth.domain.constants.AuthConstant;
import com.jcclub.auth.domain.convert.AuthUserBOConverter;
import com.jcclub.auth.domain.entity.AuthUserBO;
import com.jcclub.auth.domain.service.AuthUserDomainService;
import com.jcclub.auth.infra.basic.entity.AuthRole;
import com.jcclub.auth.infra.basic.entity.AuthUser;
import com.jcclub.auth.infra.basic.entity.AuthUserRole;
import com.jcclub.auth.infra.basic.service.IAuthRoleService;
import com.jcclub.auth.infra.basic.service.IAuthUserRoleService;
import com.jcclub.auth.infra.basic.service.IAuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private String salt = "jcclub";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        Integer count = authUserService.lambdaQuery().eq(AuthUser::getPhone, authUserBO.getPhone()).count();
        if (count > 0) {
            throw new RuntimeException("手机号码已存在");
        }
        AuthUser authUser = AuthUserBOConverter.INSTANCE.authUserBOtoEntity(authUserBO);
        authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));
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
        AuthUser authUser = AuthUserBOConverter.INSTANCE.authUserBOtoEntity(authUserBO);
        authUser.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = authUserService.updateById(authUser);
        return result;
    }
}
