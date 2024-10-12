package com.jcclub.subject.infra.basic.service;

import com.jcclub.subject.common.entity.PageResult;
import com.jcclub.subject.infra.basic.entity.SubjectInfoEs;

/**
 * @ClassName：SubjectEsService
 * @Author: gouteng
 * @Date: 2024/10/10 13:26
 * @Description: subjecteservice
 */

public interface SubjectEsService {

    Boolean insert(SubjectInfoEs subjectEsInfoEs);

    PageResult<SubjectInfoEs> querySubjectList(SubjectInfoEs subjectInfoEs);

}
