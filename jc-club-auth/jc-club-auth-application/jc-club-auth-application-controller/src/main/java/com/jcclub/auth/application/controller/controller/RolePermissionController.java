package com.jcclub.auth.application.controller.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;

import com.jcclub.auth.application.controller.convert.AuthRolePermissionDTOConverter;
import com.jcclub.auth.application.controller.dto.AuthRolePermissionDTO;
import com.jcclub.auth.common.entity.Result;
import com.jcclub.auth.domain.entity.AuthRolePermissionBO;
import com.jcclub.auth.domain.service.AuthRolePermissionDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色权限controller
 *

 */
@RestController
@RequestMapping("/rolePermission/")
@Slf4j
@RequiredArgsConstructor
public class RolePermissionController {


    private final AuthRolePermissionDomainService authRolePermissionDomainService;

    /**
     * 新增角色权限关联关系
     */
    @RequestMapping("add")
    public Result<Boolean> add(@RequestBody AuthRolePermissionDTO authRolePermissionDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("RolePermissionController.add.dto:{}", JSON.toJSONString(authRolePermissionDTO));
            }
            Preconditions.checkArgument(!CollectionUtils.isEmpty(authRolePermissionDTO.getPermissionIdList()),"权限关联不能为空");
            Preconditions.checkNotNull(authRolePermissionDTO.getRoleId(),"角色不能为空!");
            AuthRolePermissionBO rolePermissionBO = AuthRolePermissionDTOConverter.INSTANCE.convertDTOToBO(authRolePermissionDTO);
            Boolean result = authRolePermissionDomainService.add(rolePermissionBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("PermissionController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增角色权限失败");
        }
    }

}
