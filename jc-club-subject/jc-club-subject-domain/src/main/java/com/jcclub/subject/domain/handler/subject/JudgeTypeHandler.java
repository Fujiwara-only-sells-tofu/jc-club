package com.jcclub.subject.domain.handler.subject;

import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.common.enums.SubjectInfoTypeEnum;
import com.jcclub.subject.domain.convert.JudgeSubjectConverter;
import com.jcclub.subject.domain.entity.SubjectAnswerBO;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.domain.entity.SubjectOptionBO;
import com.jcclub.subject.infra.basic.entity.SubjectJudge;
import com.jcclub.subject.infra.basic.service.ISubjectJudgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName：RadioTypeHandler
 * @Author: gouteng
 * @Date: 2024/9/29 14:42
 * @Description: 判断题的题目策略
 */

@Component
@RequiredArgsConstructor
public class JudgeTypeHandler implements SubjectTypeHandler{

    private final ISubjectJudgeService subjectJudgeService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.JUDGE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        //判断题目的插入
        SubjectJudge subjectJudge = new SubjectJudge();
        SubjectAnswerBO subjectAnswerBO = subjectInfoBO.getOptionList().get(0);
        subjectJudge.setSubjectId(subjectInfoBO.getId());
        subjectJudge.setIsCorrect(subjectAnswerBO.getIsCorrect());
        subjectJudge.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectJudgeService.save(subjectJudge);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {

        List<SubjectJudge> list = subjectJudgeService.lambdaQuery().eq(SubjectJudge::getSubjectId, subjectId).list();
        List<SubjectAnswerBO> subjectAnswerBOList = JudgeSubjectConverter.INSTANCE.convertEntityToBoList(list);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setOptionList(subjectAnswerBOList);
        return subjectOptionBO;
    }
}
