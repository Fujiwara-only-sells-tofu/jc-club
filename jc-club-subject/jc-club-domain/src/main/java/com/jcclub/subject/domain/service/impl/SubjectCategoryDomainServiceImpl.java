package com.jcclub.subject.domain.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.domain.convert.SubjectCategoryConverter;
import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import com.jcclub.subject.domain.service.SubjectCategoryDomainService;
import com.jcclub.subject.infra.basic.entity.SubjectCategory;
import com.jcclub.subject.infra.basic.service.SubjectCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    private final SubjectCategoryService subjectCategoryService;

    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter
                .INSTANCE
                .convertBoToCategory(subjectCategoryBO);
        subjectCategoryService.save(subjectCategory);
    }

    @Override
    public List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        List<SubjectCategory> subjectCategories = subjectCategoryService.lambdaQuery()
                .eq(subjectCategory.getParentId()!=null,SubjectCategory::getParentId, subjectCategory.getParentId())
                .eq(subjectCategory.getCategoryType() != null,SubjectCategory::getCategoryType, subjectCategory.getCategoryType())
                .eq(SubjectCategory::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .list();
        if(CollUtil.isEmpty(subjectCategories)){
            throw new RuntimeException("查询大类失败");
        }
        List<SubjectCategoryBO> boList = SubjectCategoryConverter.INSTANCE.convertToCategoryBOList(subjectCategories);
        return boList;
    }


    @Override
    public Boolean update(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        boolean result = subjectCategoryService.updateById(subjectCategory);
        return result;
    }


    @Override
    public Boolean delete(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = subjectCategoryService.updateById(subjectCategory);
        return result;
    }
}
