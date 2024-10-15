package com.jcclub.practice.server.mapper;



import com.jcclub.practice.server.entity.dto.PracticeSubjectDTO;
import com.jcclub.practice.server.entity.po.SubjectPO;

import java.util.List;

public interface SubjectMapper {


    /**
     * 获取练习面试题目
     */
    List<SubjectPO> getPracticeSubject(PracticeSubjectDTO dto);

    SubjectPO selectById(Long subjectId);


}