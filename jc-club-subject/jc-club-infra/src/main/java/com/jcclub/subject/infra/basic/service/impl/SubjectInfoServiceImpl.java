package com.jcclub.subject.infra.basic.service.impl;

import com.jcclub.subject.infra.basic.entity.SubjectInfo;
import com.jcclub.subject.infra.basic.mapper.SubjectInfoMapper;
import com.jcclub.subject.infra.basic.service.ISubjectInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目信息表 服务实现类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-09-29
 */
@Service
public class SubjectInfoServiceImpl extends ServiceImpl<SubjectInfoMapper, SubjectInfo> implements ISubjectInfoService {

}
