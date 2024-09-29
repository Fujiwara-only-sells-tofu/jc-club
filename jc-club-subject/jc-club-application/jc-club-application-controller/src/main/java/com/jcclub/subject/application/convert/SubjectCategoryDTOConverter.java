package com.jcclub.subject.application.convert;


import com.jcclub.subject.application.dto.SubjectCategoryDTO;
import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 题目分类dto转换器
 *
 * @author: ChickenWing
 * @date: 2023/10/8
 */
@Mapper
public interface SubjectCategoryDTOConverter {

    SubjectCategoryDTOConverter INSTANCE = Mappers.getMapper(SubjectCategoryDTOConverter.class);


    SubjectCategoryBO convertDtoToCategoryBO(SubjectCategoryDTO subjectCategoryDTO);



    List<SubjectCategoryDTO> convertToCategoryDTOList(List<SubjectCategoryBO> subjectCategoryBOList);



}