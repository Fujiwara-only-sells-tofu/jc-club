package com.jcclub.subject.domain.service.impl;

import com.jcclub.subject.common.enums.CategoryTypeEnum;
import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.domain.convert.SubjectLabelConverter;
import com.jcclub.subject.domain.entity.SubjectLabelBO;
import com.jcclub.subject.domain.service.SubjectLabelDomainService;
import com.jcclub.subject.infra.basic.entity.SubjectCategory;
import com.jcclub.subject.infra.basic.entity.SubjectLabel;
import com.jcclub.subject.infra.basic.entity.SubjectMapping;
import com.jcclub.subject.infra.basic.service.ISubjectCategoryService;
import com.jcclub.subject.infra.basic.service.ISubjectLabelService;
import com.jcclub.subject.infra.basic.service.ISubjectMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {

    private final ISubjectLabelService subjectLabelService;

    private final ISubjectMappingService subjectMappingService;

    private final ISubjectCategoryService subjectCategoryService;

    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        Integer count = subjectLabelService.lambdaQuery()
                .eq(SubjectLabel::getLabelName, subjectLabelBO.getLabelName())
                .eq(SubjectLabel::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(count > 0){
            throw new RuntimeException("该标签名称已经存在");
        }
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
        Integer count = subjectLabelService.lambdaQuery()
                .eq(SubjectLabel::getId, subjectLabelBO.getId())
                .eq(SubjectLabel::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(count <= 0){
            throw new RuntimeException("标签不存在,无法删除！");
        }
        Integer mappingCount = subjectMappingService.lambdaQuery()
                .eq(SubjectMapping::getLabelId, subjectLabelBO.getId())
                .eq(SubjectMapping::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(mappingCount>0){
            throw new RuntimeException("该标签下存在关联的题目，无法删除！");
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = subjectLabelService.updateById(subjectLabel);
        return result;
    }


    @Override
    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        Long categoryId = subjectLabelBO.getCategoryId();
        SubjectCategory category = subjectCategoryService.getById(categoryId);
        List<SubjectLabel> labelList = new ArrayList<>();
        if(category.getCategoryType() == CategoryTypeEnum.PRIMARY.getCode()){
            //查询出分类下所有的标签
            labelList = subjectLabelService.lambdaQuery().eq(SubjectLabel::getCategoryId, categoryId).list();
            List<SubjectLabelBO> subjectLabelBOList = SubjectLabelConverter.INSTANCE.convertToLabelBOList(labelList);
            return subjectLabelBOList;
        }
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
