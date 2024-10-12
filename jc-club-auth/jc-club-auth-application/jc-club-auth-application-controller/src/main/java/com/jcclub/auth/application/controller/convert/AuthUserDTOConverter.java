package com.jcclub.auth.application.controller.convert;

import com.jcclub.auth.domain.entity.AuthUserBO;
import com.jcclub.auth.entity.AuthUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @ClassName：AuthUserDTOConvert
 * @Author: gouteng
 * @Date: 2024/10/4 13:59
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Mapper
public interface AuthUserDTOConverter {

    AuthUserDTOConverter INSTANCE = Mappers.getMapper(AuthUserDTOConverter.class);

    AuthUserBO authUserDTOtoBO(AuthUserDTO authUserDTO);


    AuthUserDTO authUserBOtoDTO(AuthUserBO authUserBO);





}
