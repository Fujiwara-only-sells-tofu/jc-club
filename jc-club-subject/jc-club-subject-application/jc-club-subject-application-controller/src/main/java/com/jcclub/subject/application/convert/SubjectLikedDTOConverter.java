package com.jcclub.subject.application.convert;

import com.jcclub.subject.application.dto.SubjectLikedDTO;
import com.jcclub.subject.domain.entity.SubjectLikedBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 题目点赞表 dto转换器
 *
 * @author ZCY
 * @since 2024-10-12 10:16:18
 */
@Mapper
public interface SubjectLikedDTOConverter {

    SubjectLikedDTOConverter INSTANCE = Mappers.getMapper(SubjectLikedDTOConverter.class);

    SubjectLikedBO convertDTOToBO(SubjectLikedDTO subjectLikedDTO);

    SubjectLikedDTO convertBOToDTO(SubjectLikedBO subjectLikedBO);

}
