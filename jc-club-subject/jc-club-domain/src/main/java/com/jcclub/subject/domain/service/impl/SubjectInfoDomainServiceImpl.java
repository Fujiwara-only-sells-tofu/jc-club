package com.jcclub.subject.domain.service.impl;

import com.jcclub.subject.domain.convert.SubjectInfoConverter;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.domain.handler.subject.SubjectTypeHandler;
import com.jcclub.subject.domain.handler.subject.SubjectTypeHandlerFactory;
import com.jcclub.subject.domain.service.SubjectInfoDomainService;
import com.jcclub.subject.infra.basic.entity.SubjectInfo;
import com.jcclub.subject.infra.basic.entity.SubjectMapping;
import com.jcclub.subject.infra.basic.service.ISubjectInfoService;
import com.jcclub.subject.infra.basic.service.ISubjectMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {

    private final ISubjectInfoService subjectInfoService;

    private final SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    private final ISubjectMappingService subjectMappingService;
    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBoToInfo(subjectInfoBO);
        subjectInfoService.save(subjectInfo);
        //使用工厂进行根据类型自动映射处理
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
        handler.add(subjectInfoBO);

        //要将映射关系添加到mapping表中
        List<Integer> categoryIds = subjectInfoBO.getCategoryIds();
        List<Integer> labelIds = subjectInfoBO.getLabelIds();
        LinkedList<SubjectMapping> subjectMappings = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfo.getId());
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setLabelId(Long.valueOf(labelId));
                subjectMappings.add(subjectMapping);
            });
        });
        subjectMappingService.saveBatch(subjectMappings);
    }
}
