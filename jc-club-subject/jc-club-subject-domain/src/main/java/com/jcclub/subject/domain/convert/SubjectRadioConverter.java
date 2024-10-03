package com.jcclub.subject.domain.convert;

import com.jcclub.subject.domain.entity.SubjectAnswerBO;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.infra.basic.entity.SubjectInfo;
import com.jcclub.subject.infra.basic.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SubjectRadioConverter {

    SubjectRadioConverter INSTANCE = Mappers.getMapper(SubjectRadioConverter.class);

    SubjectRadio convertBoToRadio(SubjectAnswerBO subjectAnswerBO);



}