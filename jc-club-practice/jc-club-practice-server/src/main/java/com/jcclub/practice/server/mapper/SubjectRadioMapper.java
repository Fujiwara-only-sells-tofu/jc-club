package com.jcclub.practice.server.mapper;


import com.jcclub.practice.server.entity.po.SubjectRadioPO;

import java.util.List;

public interface SubjectRadioMapper {

    /**
     * 根据题目id查询单选题目
     */
    List<SubjectRadioPO> selectBySubjectId(Long subjectId);

}
