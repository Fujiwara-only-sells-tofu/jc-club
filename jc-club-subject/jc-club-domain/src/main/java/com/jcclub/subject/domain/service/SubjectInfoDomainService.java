package com.jcclub.subject.domain.service;

import com.jcclub.subject.common.entity.PageResult;
import com.jcclub.subject.common.entity.SubjectPageQuery;
import com.jcclub.subject.domain.entity.SubjectInfoBO;

public interface SubjectInfoDomainService {


    /**
     * @Description:添加题目信息
     * @data:[subjectInfoBO]
     * @return: void
     * @Author: ZCY
     * @Date: 2024-09-29 14:08:09
     */

    void add(SubjectInfoBO subjectInfoBO);

    /**
     * @Description:分页查询
     * @data:[subjectInfoBO]
     * @return: com.jcclub.subject.common.entity.PageResult<com.jcclub.subject.domain.entity.SubjectInfoBO>
     * @Author: ZCY
     * @Date: 2024-09-29 16:47:57
     */

    PageResult<SubjectInfoBO> getSubjectPage(SubjectPageQuery query);

    /**
     * @Description:查询题目信息
     * @data:[subjectInfoBO]
     * @return: com.jcclub.subject.domain.entity.SubjectInfoBO
     * @Author: ZCY
     * @Date: 2024-10-01 14:50:12
     */

    SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO);
}
