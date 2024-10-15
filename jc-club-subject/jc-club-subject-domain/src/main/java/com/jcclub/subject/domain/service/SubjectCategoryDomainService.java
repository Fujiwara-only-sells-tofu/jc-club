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

    /**
     * @Description:查询分类及标签一次性
     * @data:[subjectCategoryBO]
     * @return: java.util.List<com.jcclub.subject.domain.entity.SubjectCategoryBO>
     * @Author: ZCY
     * @Date: 2024-10-05 20:32:58
     */

    List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO);

    /**
    *@Title: queryCategoryByPrimary
    * @Author: 张辰逸
    * @Date: 2024-10-14 10:06:03
    * @Params: [subjectCategoryBO]
    * @Return: List<SubjectCategoryBO>
    * @Description: 根据大类查询二级分类信息
     */

    List<SubjectCategoryBO> queryCategoryByPrimary(SubjectCategoryBO subjectCategoryBO);
}
