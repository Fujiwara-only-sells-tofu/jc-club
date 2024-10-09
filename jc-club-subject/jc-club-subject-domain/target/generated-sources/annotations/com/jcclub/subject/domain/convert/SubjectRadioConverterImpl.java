package com.jcclub.subject.domain.convert;

import com.jcclub.subject.domain.entity.SubjectAnswerBO;
import com.jcclub.subject.infra.basic.entity.SubjectRadio;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-08T10:49:44+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_412 (Amazon.com Inc.)"
)
public class SubjectRadioConverterImpl implements SubjectRadioConverter {

    @Override
    public SubjectRadio convertBoToRadio(SubjectAnswerBO subjectAnswerBO) {
        if ( subjectAnswerBO == null ) {
            return null;
        }

        SubjectRadio subjectRadio = new SubjectRadio();

        subjectRadio.setOptionType( subjectAnswerBO.getOptionType() );
        subjectRadio.setOptionContent( subjectAnswerBO.getOptionContent() );
        subjectRadio.setIsCorrect( subjectAnswerBO.getIsCorrect() );

        return subjectRadio;
    }
}
