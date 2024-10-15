package com.jcclub.practice.server.mapper;


import com.jcclub.practice.server.entity.po.SubjectJudgePO;

public interface SubjectJudgeMapper {


    SubjectJudgePO selectBySubjectId(Long repeatSubjectId);


}