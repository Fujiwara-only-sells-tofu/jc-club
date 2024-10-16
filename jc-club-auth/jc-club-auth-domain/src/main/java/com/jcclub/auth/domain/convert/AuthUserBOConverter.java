package com.jcclub.auth.domain.convert;

import com.jcclub.auth.domain.entity.AuthUserBO;
import com.jcclub.auth.infra.basic.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @ClassName：AuthUserDTOConvert
 * @Author: gouteng
 * @Date: 2024/10/4 13:59
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Mapper
public interface AuthUserBOConverter {

    AuthUserBOConverter INSTANCE = Mappers.getMapper(AuthUserBOConverter.class);

    AuthUserBO authUsertoBO(AuthUser authUser);

    List<AuthUserBO> authUsertoBO(List<AuthUser> authUsers);


    AuthUser authUserBOtoEntity(AuthUserBO authUserBO);





}
