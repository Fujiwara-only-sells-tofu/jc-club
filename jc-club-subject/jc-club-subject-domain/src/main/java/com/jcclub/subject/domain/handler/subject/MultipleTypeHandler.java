package com.jcclub.subject.domain.handler.subject;

import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.common.enums.SubjectInfoTypeEnum;
import com.jcclub.subject.domain.convert.MultipleSubjectConverter;
import com.jcclub.subject.domain.entity.SubjectAnswerBO;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.domain.entity.SubjectOptionBO;
import com.jcclub.subject.infra.basic.entity.SubjectMultiple;
import com.jcclub.subject.infra.basic.service.ISubjectMultipleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName：RadioTypeHandler
 * @Author: gouteng
 * @Date: 2024/9/29 14:42
 * @Description: 多选的题目策略
 */

@Component
@RequiredArgsConstructor
public class MultipleTypeHandler implements SubjectTypeHandler{

    private final ISubjectMultipleService subjectMultipleService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.MULTIPLE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        //多选题目的插入
        List<SubjectMultiple> subjectMultipleList = new LinkedList<>();
        subjectInfoBO.getOptionList().forEach(option -> {
            SubjectMultiple subjectMultiple = MultipleSubjectConverter.INSTANCE.convertBoToEntity(option);
            subjectMultiple.setSubjectId(subjectInfoBO.getId());
            subjectMultiple.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            subjectMultipleList.add(subjectMultiple);
        });
        subjectMultipleService.saveBatch(subjectMultipleList);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {

        List<SubjectMultiple> list = subjectMultipleService.lambdaQuery().eq(SubjectMultiple::getSubjectId, subjectId).list();
        List<SubjectAnswerBO> subjectAnswerBOList = MultipleSubjectConverter.INSTANCE.convertEntityToBoList(list);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setOptionList(subjectAnswerBOList);
        return subjectOptionBO;
    }
}
