package com.jcclub.practice.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcclub.practice.server.entity.dto.PracticeSetDTO;
import com.jcclub.practice.server.entity.po.PracticeSetPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 套题信息表 Mapper 接口
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-13
 */


public interface PracticeSetMapper extends BaseMapper<PracticeSetPO> {


    /**
     * 新增套题
     */
    int add(PracticeSetPO po);

    PracticeSetPO selectById(Long setId);

    int updateHeat(Long setId);

    /**
     * 获取模拟考卷列表数量
     */
    Integer getListCount(PracticeSetDTO dto);

    /**
     * 获取模拟考卷列表
     */
    List<PracticeSetPO> getSetList(@Param("dto") PracticeSetDTO dto,
                                   @Param("limit") int limit,
                                   @Param("offset") int offset);
}
