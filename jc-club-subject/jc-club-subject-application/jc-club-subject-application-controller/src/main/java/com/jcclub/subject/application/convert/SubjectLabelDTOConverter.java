package com.jcclub.subject.application.convert;


import com.jcclub.subject.application.dto.SubjectCategoryDTO;
import com.jcclub.subject.application.dto.SubjectLabelDTO;
import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import com.jcclub.subject.domain.entity.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 题目标签dto转换器
 *
 * @author: ChickenWing
 * @date: 2023/10/8
 */
@Mapper
public interface SubjectLabelDTOConverter {

    SubjectLabelDTOConverter INSTANCE = Mappers.getMapper(SubjectLabelDTOConverter.class);


    SubjectLabelBO convertDtoToLabelBO(SubjectLabelDTO subjectLabelDTO);



    List<SubjectLabelDTO> convertToLabelDTOList(List<SubjectLabelBO> subjectLabelBOList);



}