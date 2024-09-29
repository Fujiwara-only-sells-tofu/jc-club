package com.jcclub.subject.domain.handler.subject;

import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.common.enums.SubjectInfoTypeEnum;
import com.jcclub.subject.domain.convert.BriefSubjectConverter;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.infra.basic.entity.SubjectBrief;
import com.jcclub.subject.infra.basic.service.ISubjectBriefService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @ClassName：RadioTypeHandler
 * @Author: gouteng
 * @Date: 2024/9/29 14:42
 * @Description: 简答题的题目策略
 */

@Component
@RequiredArgsConstructor
public class BriefTypeHandler implements SubjectTypeHandler{

    private final ISubjectBriefService subjectBriefService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        SubjectBrief subjectBrief = BriefSubjectConverter.INSTANCE.convertBoToEntity(subjectInfoBO);
        subjectBrief.setSubjectId(subjectInfoBO.getId().intValue());
        subjectBrief.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectBriefService.save(subjectBrief);
    }
}
