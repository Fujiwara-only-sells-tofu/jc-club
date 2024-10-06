package com.jcclub.auth.infra.basic.service.impl;

import com.jcclub.auth.infra.basic.entity.AuthPermission;
import com.jcclub.auth.infra.basic.mapper.AuthPermissionMapper;
import com.jcclub.auth.infra.basic.service.IAuthPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class AuthPermissionServiceImpl extends ServiceImpl<AuthPermissionMapper, AuthPermission> implements IAuthPermissionService {

}
