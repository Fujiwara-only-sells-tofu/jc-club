package com.jcclub.subject.domain.service;

import com.jcclub.subject.common.entity.PageResult;
import com.jcclub.subject.common.entity.SubjectPageQuery;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.infra.basic.entity.SubjectInfoEs;

import java.util.List;

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

    /**
     * @Description: 根据关键字进行全文检索
     * @data:[subjectInfoEs]
     * @return: com.jcclub.subject.common.entity.PageResult<com.jcclub.subject.infra.basic.entity.SubjectInfoEs>
     * @Author: ZCY
     * @Date: 2024-10-11 17:21:36
     */

    PageResult<SubjectInfoEs> getSubjectPageBySearch(SubjectInfoEs subjectInfoEs);

    /**
     * @Description: 获取贡献榜信息
     * @data:[]
     * @return: java.util.List<com.jcclub.subject.domain.entity.SubjectInfoBO>
     * @Author: ZCY
     * @Date: 2024-10-11 20:54:22
     */
    
    List<SubjectInfoBO> getContributeList();
}
