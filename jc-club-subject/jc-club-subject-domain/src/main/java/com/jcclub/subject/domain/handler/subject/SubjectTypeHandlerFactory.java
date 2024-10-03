package com.jcclub.subject.domain.handler.subject;

import com.jcclub.subject.common.enums.SubjectInfoTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName：SubjectTypeHandlerFactory
 * @Author: gouteng
 * @Date: 2024/9/29 14:46
 * @Description: 题目类型的工厂
 */

@Component
@RequiredArgsConstructor
public class SubjectTypeHandlerFactory implements InitializingBean {

    private final List<SubjectTypeHandler> subjectTypeHandlerList;

    HashMap<SubjectInfoTypeEnum,SubjectTypeHandler> handlerMap = new HashMap<>();

    public SubjectTypeHandler getHandler(int subjectType){
        SubjectInfoTypeEnum subjectInfoTypeEnum = SubjectInfoTypeEnum.getByCode(subjectType);
        return handlerMap.get(subjectInfoTypeEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (SubjectTypeHandler subjectTypeHandler : subjectTypeHandlerList) {
            handlerMap.put(subjectTypeHandler.getHandlerType(),subjectTypeHandler);
        }
    }
}
