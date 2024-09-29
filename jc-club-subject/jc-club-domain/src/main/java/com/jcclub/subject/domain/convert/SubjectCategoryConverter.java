package com.jcclub.subject.domain.convert;

import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import com.jcclub.subject.infra.basic.entity.SubjectCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SubjectCategoryConverter {

    SubjectCategoryConverter INSTANCE = Mappers.getMapper(SubjectCategoryConverter.class);

    SubjectCategory convertBoToCategory(SubjectCategoryBO subjectCategoryBO);

    List<SubjectCategoryBO> convertToCategoryBOList(List<SubjectCategory> subjectCategoryList);


}