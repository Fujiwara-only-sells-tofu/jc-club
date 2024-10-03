package com.jcclub.subject.domain.handler.subject;

import com.jcclub.subject.common.enums.SubjectInfoTypeEnum;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.domain.entity.SubjectOptionBO;

/**
 * @ClassName：SubjectTypeHandler
 * @Author: gouteng
 * @Date: 2024/9/29 14:37
 * @Description: 题目类型处理器
 */

public interface SubjectTypeHandler {

    /*
     * @Description:枚举身份的识别
     * @data:* @param null
     * @return: 
     * @return: null
     * @Author: ZCY
     * @Date: 2024-09-29 14:41:12
     */


    SubjectInfoTypeEnum getHandlerType();
    /*
     * @Description:实际题目的插入
     * @data:* @param null
     * @return:
     * @return: null
     * @Author: ZCY
     * @Date: 2024-09-29 14:41:32
     */

    void add(SubjectInfoBO subjectInfoBO);

    SubjectOptionBO query(int subjectId);
}
