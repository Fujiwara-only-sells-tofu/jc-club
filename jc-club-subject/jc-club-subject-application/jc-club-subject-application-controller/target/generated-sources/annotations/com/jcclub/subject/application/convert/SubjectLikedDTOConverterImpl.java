package com.jcclub.subject.application.convert;

import com.jcclub.subject.application.dto.SubjectLikedDTO;
import com.jcclub.subject.domain.entity.SubjectLikedBO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-15T16:50:36+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_412 (Amazon.com Inc.)"
)
public class SubjectLikedDTOConverterImpl implements SubjectLikedDTOConverter {

    @Override
    public SubjectLikedBO convertDTOToBO(SubjectLikedDTO subjectLikedDTO) {
        if ( subjectLikedDTO == null ) {
            return null;
        }

        SubjectLikedBO subjectLikedBO = new SubjectLikedBO();

        subjectLikedBO.setPageNo( subjectLikedDTO.getPageNo() );
        subjectLikedBO.setPageSize( subjectLikedDTO.getPageSize() );
        subjectLikedBO.setId( subjectLikedDTO.getId() );
        subjectLikedBO.setSubjectId( subjectLikedDTO.getSubjectId() );
        subjectLikedBO.setSubjectName( subjectLikedDTO.getSubjectName() );
        subjectLikedBO.setLikeUserId( subjectLikedDTO.getLikeUserId() );
        subjectLikedBO.setStatus( subjectLikedDTO.getStatus() );
        subjectLikedBO.setCreatedBy( subjectLikedDTO.getCreatedBy() );
        subjectLikedBO.setCreatedTime( subjectLikedDTO.getCreatedTime() );
        subjectLikedBO.setUpdateBy( subjectLikedDTO.getUpdateBy() );
        subjectLikedBO.setUpdateTime( subjectLikedDTO.getUpdateTime() );
        subjectLikedBO.setIsDeleted( subjectLikedDTO.getIsDeleted() );

        return subjectLikedBO;
    }

    @Override
    public SubjectLikedDTO convertBOToDTO(SubjectLikedBO subjectLikedBO) {
        if ( subjectLikedBO == null ) {
            return null;
        }

        SubjectLikedDTO subjectLikedDTO = new SubjectLikedDTO();

        subjectLikedDTO.setPageNo( subjectLikedBO.getPageNo() );
        subjectLikedDTO.setPageSize( subjectLikedBO.getPageSize() );
        subjectLikedDTO.setId( subjectLikedBO.getId() );
        subjectLikedDTO.setSubjectId( subjectLikedBO.getSubjectId() );
        subjectLikedDTO.setSubjectName( subjectLikedBO.getSubjectName() );
        subjectLikedDTO.setLikeUserId( subjectLikedBO.getLikeUserId() );
        subjectLikedDTO.setStatus( subjectLikedBO.getStatus() );
        subjectLikedDTO.setCreatedBy( subjectLikedBO.getCreatedBy() );
        subjectLikedDTO.setCreatedTime( subjectLikedBO.getCreatedTime() );
        subjectLikedDTO.setUpdateBy( subjectLikedBO.getUpdateBy() );
        subjectLikedDTO.setUpdateTime( subjectLikedBO.getUpdateTime() );
        subjectLikedDTO.setIsDeleted( subjectLikedBO.getIsDeleted() );

        return subjectLikedDTO;
    }
}
