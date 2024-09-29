package com.jcclub.subject.domain.convert;

import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.domain.entity.SubjectLabelBO;
import com.jcclub.subject.infra.basic.entity.SubjectInfo;
import com.jcclub.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SubjectInfoConverter {

    SubjectInfoConverter INSTANCE = Mappers.getMapper(SubjectInfoConverter.class);

    SubjectInfo convertBoToInfo(SubjectInfoBO subjectInfoBO);

    List<SubjectInfoBO> convertToLabelBOList(List<SubjectInfo> subjectInfoList);


}