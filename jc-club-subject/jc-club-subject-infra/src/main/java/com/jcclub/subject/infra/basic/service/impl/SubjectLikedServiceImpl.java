package com.jcclub.subject.infra.basic.service.impl;

import com.jcclub.subject.infra.basic.entity.SubjectLiked;
import com.jcclub.subject.infra.basic.mapper.SubjectLikedMapper;
import com.jcclub.subject.infra.basic.service.ISubjectLikedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题目点赞表 服务实现类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-12
 */
@Service
@RequiredArgsConstructor
public class SubjectLikedServiceImpl extends ServiceImpl<SubjectLikedMapper, SubjectLiked> implements ISubjectLikedService {

    private final SubjectLikedMapper subjectLikedMapper;

    @Override
    public void batchInsertOrUpdate(List<SubjectLiked> subjectLikedList) {
        subjectLikedMapper.batchInsertOrUpdate(subjectLikedList);
    }
}
