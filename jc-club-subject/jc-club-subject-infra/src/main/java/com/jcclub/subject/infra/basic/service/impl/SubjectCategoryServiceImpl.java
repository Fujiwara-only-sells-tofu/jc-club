package com.jcclub.subject.infra.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcclub.subject.infra.basic.entity.SubjectCategory;
import com.jcclub.subject.infra.basic.mapper.SubjectCategoryMapper;
import com.jcclub.subject.infra.basic.service.ISubjectCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 题目分类(SubjectCategory)表服务实现类
 *
 * @author makejava
 * @since 2024-09-28 13:55:50
 */
@Service("subjectCategoryService")
public class SubjectCategoryServiceImpl extends ServiceImpl<SubjectCategoryMapper, SubjectCategory> implements ISubjectCategoryService {
    @Resource
    private SubjectCategoryMapper subjectCategoryDao;


}
