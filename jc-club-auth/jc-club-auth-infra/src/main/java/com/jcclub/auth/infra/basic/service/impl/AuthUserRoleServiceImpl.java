package com.jcclub.auth.infra.basic.service.impl;

import com.jcclub.auth.infra.basic.entity.AuthUserRole;
import com.jcclub.auth.infra.basic.mapper.AuthUserRoleMapper;
import com.jcclub.auth.infra.basic.service.IAuthUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-04
 */
@Service
public class AuthUserRoleServiceImpl extends ServiceImpl<AuthUserRoleMapper, AuthUserRole> implements IAuthUserRoleService {

}
