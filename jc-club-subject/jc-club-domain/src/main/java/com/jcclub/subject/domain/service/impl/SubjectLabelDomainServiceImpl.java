package com.jcclub.subject.domain.service.impl;

import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.domain.convert.SubjectLabelConverter;
import com.jcclub.subject.domain.entity.SubjectLabelBO;
import com.jcclub.subject.domain.service.SubjectLabelDomainService;
import com.jcclub.subject.infra.basic.entity.SubjectLabel;
import com.jcclub.subject.infra.basic.entity.SubjectMapping;
import com.jcclub.subject.infra.basic.service.SubjectLabelService;
import com.jcclub.subject.infra.basic.service.SubjectMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {

    private final SubjectLabelService subjectLabelService;

    private final SubjectMappingService subjectMappingService;

    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        boolean result = subjectLabelService.save(subjectLabel);
        return result;
    }

    @Override
    public Boolean update(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        boolean result = subjectLabelService.updateById(subjectLabel);
        return result;
    }

    @Override
    public Boolean delete(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = subjectLabelService.updateById(subjectLabel);
        return result;
    }


    @Override
    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        //先去映射表查出标签id
        List<SubjectMapping> list = subjectMappingService.lambdaQuery().eq(SubjectMapping::getCategoryId, subjectLabelBO.getCategoryId())
                .eq(SubjectMapping::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .list();
        List<Long> labelIdList = list.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> subjectLabels = subjectLabelService.listByIds(labelIdList);
        List<SubjectLabelBO> subjectLabelBOList = SubjectLabelConverter.INSTANCE.convertToLabelBOList(subjectLabels);
        return subjectLabelBOList;
    }
}
