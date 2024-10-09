package com.jcclub.auth.application.controller.controller;

import com.google.common.base.Preconditions;
import com.jcclub.auth.application.controller.convert.AuthRoleDTOConverter;
import com.jcclub.auth.application.controller.dto.AuthRoleDTO;
import com.jcclub.auth.common.entity.Result;
import com.jcclub.auth.domain.entity.AuthRoleBO;
import com.jcclub.auth.domain.service.AuthRoleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName：RoleController
 * @Author: gouteng
 * @Date: 2024/10/4 16:26
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@RequestMapping("/role")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    private final AuthRoleDomainService authRoleDomainService;

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody AuthRoleDTO authRoleDTO){
        try {
            log.info("添加角色{}", authRoleDTO);
            Preconditions.checkArgument(!StringUtils.isBlank(authRoleDTO.getRoleKey()), "角色key不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(authRoleDTO.getRoleName()), "角色名称不能为空");

            AuthRoleBO authRoleBO = AuthRoleDTOConverter.INSTANCE.convertDTOToBO(authRoleDTO);
            Boolean result = authRoleDomainService.add(authRoleBO);
            return Result.ok(result);
        }catch (Exception e){
            log.error("添加角色失败",e);
            return Result.fail(e.getMessage());
        }
    }


    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody AuthRoleDTO authRoleDTO){
        try {
            log.info("更新角色{}", authRoleDTO);
            Preconditions.checkArgument(authRoleDTO.getId() != null, "角色id不能为空");
            AuthRoleBO authRoleBO = AuthRoleDTOConverter.INSTANCE.convertDTOToBO(authRoleDTO);
            Boolean result = authRoleDomainService.update(authRoleBO);
            return Result.ok(result);
        }catch (Exception e){
            log.error("更新角色失败",e);
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody AuthRoleDTO authRoleDTO){
        try {
            log.info("删除角色{}", authRoleDTO);
            Preconditions.checkArgument(authRoleDTO.getId() != null, "角色id不能为空");
            AuthRoleBO authRoleBO = AuthRoleDTOConverter.INSTANCE.convertDTOToBO(authRoleDTO);
            Boolean result = authRoleDomainService.delete(authRoleBO);
            return Result.ok(result);
        }catch (Exception e){
            log.error("删除角色失败",e);
            return Result.fail(e.getMessage());
        }
    }
}
