package com.jcclub.subject.domain.service;

import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import com.jcclub.subject.domain.entity.SubjectLabelBO;

import java.util.List;

public interface SubjectLabelDomainService {



    /**
     * @Description:新增标签
     * @data:[subjectLabelBO]
     * @return: java.lang.Boolean
     * @Author: ZCY
     * @Date: 2024-09-29 10:27:50
     */

    Boolean add(SubjectLabelBO subjectLabelBO);

    /**
     * @Description:更新标签
     * @data:[subjectLabelBO]
     * @return: java.lang.Boolean
     * @Author: ZCY
     * @Date: 2024-09-29 10:27:39
     */

    Boolean update(SubjectLabelBO subjectLabelBO);
    /**
     * @Description:删除标签
     * @data:[subjectLabelBO]
     * @return: java.lang.Boolean
     * @Author: ZCY
     * @Date: 2024-09-29 10:30:39
     */

    Boolean delete(SubjectLabelBO subjectLabelBO);

    /**
     * @Description:根据分类id查询标签
     * @data:[subjectLabelBO]
     * @return: java.util.List<com.jcclub.subject.domain.entity.SubjectLabelBO>
     * @Author: ZCY
     * @Date: 2024-09-29 10:48:49
     */

    List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO);
}
