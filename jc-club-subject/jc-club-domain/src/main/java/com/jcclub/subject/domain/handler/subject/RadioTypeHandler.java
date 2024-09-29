package com.jcclub.subject.domain.handler.subject;

import cn.hutool.core.collection.CollUtil;
import com.jcclub.subject.common.enums.SubjectInfoTypeEnum;
import com.jcclub.subject.domain.convert.SubjectRadioConverter;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.infra.basic.entity.SubjectRadio;
import com.jcclub.subject.infra.basic.service.ISubjectRadioService;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * @ClassName：RadioTypeHandler
 * @Author: gouteng
 * @Date: 2024/9/29 14:42
 * @Description: 单选的题目策略
 */
@Component
public class RadioTypeHandler implements SubjectTypeHandler{

    private ISubjectRadioService subjectRadioService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        //单选题目的插入
        LinkedList<SubjectRadio> subjectRadios = new LinkedList<>();
        if(CollUtil.isEmpty(subjectInfoBO.getOptionList())){
            throw new RuntimeException("单选题目选项不能为空");
        }
        subjectInfoBO.getOptionList().forEach(option -> {
            SubjectRadio subjectRadio = SubjectRadioConverter.INSTANCE.convertBoToRadio(option);
            subjectRadio.setSubjectId(subjectInfoBO.getId());
            subjectRadios.add(subjectRadio);
        });
        subjectRadioService.saveBatch(subjectRadios);
    }
}
