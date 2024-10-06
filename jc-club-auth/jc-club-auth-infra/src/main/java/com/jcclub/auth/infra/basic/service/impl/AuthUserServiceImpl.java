package com.jcclub.auth.infra.basic.service.impl;

import com.jcclub.auth.infra.basic.entity.AuthUser;
import com.jcclub.auth.infra.basic.mapper.AuthUserMapper;
import com.jcclub.auth.infra.basic.service.IAuthUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-04
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserService {

}
