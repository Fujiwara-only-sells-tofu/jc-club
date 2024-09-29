package com.jcclub.subject.domain.convert;

import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import com.jcclub.subject.domain.entity.SubjectLabelBO;
import com.jcclub.subject.infra.basic.entity.SubjectCategory;
import com.jcclub.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SubjectLabelConverter {

    SubjectLabelConverter INSTANCE = Mappers.getMapper(SubjectLabelConverter.class);

    SubjectLabel convertBoToLabel(SubjectLabelBO subjectLabelBO);

    List<SubjectLabelBO> convertToLabelBOList(List<SubjectLabel> subjectLabelList);


}