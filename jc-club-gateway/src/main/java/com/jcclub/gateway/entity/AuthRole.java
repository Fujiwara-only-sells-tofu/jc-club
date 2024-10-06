package com.jcclub.gateway.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class AuthRole implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色唯一标识
     */
    private String roleKey;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否被删除 0未删除 1已删除
     */
    private Integer isDeleted;


}
