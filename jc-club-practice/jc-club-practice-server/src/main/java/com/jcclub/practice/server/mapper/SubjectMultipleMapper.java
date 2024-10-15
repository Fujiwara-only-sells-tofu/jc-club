package com.jcclub.practice.server.mapper;


import com.jcclub.practice.server.entity.po.SubjectMultiplePO;

import java.util.List;

public interface SubjectMultipleMapper {

    /**
     * 查询题目
     */
    List<SubjectMultiplePO> selectBySubjectId(Long subjectId);

}