package com.jcclub.subject.infra.basic.service;

import com.jcclub.subject.infra.basic.entity.SubjectLiked;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题目点赞表 服务类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-12
 */
public interface ISubjectLikedService extends IService<SubjectLiked> {

    void batchInsertOrUpdate(List<SubjectLiked> subjectLikedList);
}
