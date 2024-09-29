package com.jcclub.subject.infra.basic.service.impl;

import com.jcclub.subject.infra.basic.entity.SubjectMapping;
import com.jcclub.subject.infra.basic.mapper.SubjectMappingMapper;
import com.jcclub.subject.infra.basic.service.ISubjectMappingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目分类关系表 服务实现类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-09-29
 */
@Service
public class SubjectMappingServiceImpl extends ServiceImpl<SubjectMappingMapper, SubjectMapping> implements ISubjectMappingService {

}
