package com.jcclub.subject.domain.service;

import com.jcclub.subject.domain.entity.SubjectCategoryBO;

import java.util.List;

public interface SubjectCategoryDomainService {

    /*
    * 新增分类
    * */
    void add(SubjectCategoryBO subjectCategoryBO);

    /*
    * 查询大类
    * */
    List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO);

    /*
    * 更新分类信息
    * */
    Boolean update(SubjectCategoryBO subjectCategoryBO);

    /**
     * @Description:删除分类
     * @data:[subjectCategoryBO]
     * @return: java.lang.Boolean
     * @Author: ZCY
     * @Date: 2024-09-29 09:10:12
     */

    Boolean delete(SubjectCategoryBO subjectCategoryBO);
}
