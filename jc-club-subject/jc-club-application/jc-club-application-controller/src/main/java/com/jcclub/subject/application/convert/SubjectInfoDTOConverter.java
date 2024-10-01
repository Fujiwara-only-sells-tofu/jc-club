package com.jcclub.subject.application.convert;


import com.jcclub.subject.application.dto.SubjectInfoDTO;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
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
public interface SubjectInfoDTOConverter {

    SubjectInfoDTOConverter INSTANCE = Mappers.getMapper(SubjectInfoDTOConverter.class);


    SubjectInfoBO convertDtoToInfoBO(SubjectInfoDTO subjectInfoDTO);

    SubjectInfoDTO convertBOToInfoDTO(SubjectInfoBO subjectInfoBO);


    List<SubjectInfoDTO> convertToInfoDTOList(List<SubjectInfoBO> subjectInfoBOList);



}