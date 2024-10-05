package com.jcclub.subject.application.convert;


import com.jcclub.subject.application.dto.SubjectAnswerDTO;
import com.jcclub.subject.domain.entity.SubjectAnswerBO;
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
public interface SubjectAnswerDTOConverter {

    SubjectAnswerDTOConverter INSTANCE = Mappers.getMapper(SubjectAnswerDTOConverter.class);


    SubjectAnswerBO convertDtoToAnswerBO(SubjectAnswerDTO subjectAnswerDTO);

    List<SubjectAnswerBO> convertDtoToAnswerBOList( List<SubjectAnswerDTO> subjectAnswerDTOList);

    List<SubjectAnswerDTO> convertToAnswerDTOList(List<SubjectAnswerBO> subjectAnswerBOList);



}