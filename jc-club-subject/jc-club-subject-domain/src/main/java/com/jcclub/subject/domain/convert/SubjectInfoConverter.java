package com.jcclub.subject.domain.convert;

import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.domain.entity.SubjectOptionBO;
import com.jcclub.subject.infra.basic.entity.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.security.auth.Subject;
import java.util.List;


@Mapper
public interface SubjectInfoConverter {

    SubjectInfoConverter INSTANCE = Mappers.getMapper(SubjectInfoConverter.class);

    SubjectInfo convertBoToInfo(SubjectInfoBO subjectInfoBO);

    SubjectInfoBO convertOptionToBO(SubjectOptionBO subjectOptionBO);

    SubjectInfoBO convertOptionAndInfoToBO(SubjectOptionBO subjectOptionBO,SubjectInfo subjectInfo);

    List<SubjectInfoBO> convertToInfoBOList(List<SubjectInfo> subjectInfoList);


}