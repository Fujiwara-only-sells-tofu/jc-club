package com.jcclub.subject.infra.basic.mapper;

import com.jcclub.subject.infra.basic.entity.SubjectLiked;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 题目点赞表 Mapper 接口
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-12
 */
public interface SubjectLikedMapper extends BaseMapper<SubjectLiked> {

    void batchInsertOrUpdate(@Param("entities") List<SubjectLiked> subjectLikedList);
}
