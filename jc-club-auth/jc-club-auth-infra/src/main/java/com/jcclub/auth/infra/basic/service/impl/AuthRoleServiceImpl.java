package com.jcclub.auth.infra.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcclub.auth.infra.basic.entity.AuthRole;
import com.jcclub.auth.infra.basic.mapper.AuthRoleMapper;
import com.jcclub.auth.infra.basic.service.IAuthRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-04
 */
@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole> implements IAuthRoleService {

}
