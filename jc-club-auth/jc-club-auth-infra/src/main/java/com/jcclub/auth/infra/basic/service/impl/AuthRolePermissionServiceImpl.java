package com.jcclub.auth.infra.basic.service.impl;

import com.jcclub.auth.infra.basic.entity.AuthRolePermission;
import com.jcclub.auth.infra.basic.mapper.AuthRolePermissionMapper;
import com.jcclub.auth.infra.basic.service.IAuthRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-05
 */
@Service
public class AuthRolePermissionServiceImpl extends ServiceImpl<AuthRolePermissionMapper, AuthRolePermission> implements IAuthRolePermissionService {

}
