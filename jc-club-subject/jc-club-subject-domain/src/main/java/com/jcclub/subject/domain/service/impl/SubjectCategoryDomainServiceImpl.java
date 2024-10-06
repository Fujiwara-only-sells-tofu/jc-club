package com.jcclub.subject.domain.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.domain.convert.SubjectCategoryConverter;
import com.jcclub.subject.domain.convert.SubjectLabelConverter;
import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import com.jcclub.subject.domain.entity.SubjectLabelBO;
import com.jcclub.subject.domain.service.SubjectCategoryDomainService;
import com.jcclub.subject.infra.basic.entity.SubjectCategory;
import com.jcclub.subject.infra.basic.entity.SubjectLabel;
import com.jcclub.subject.infra.basic.entity.SubjectMapping;
import com.jcclub.subject.infra.basic.service.ISubjectCategoryService;
import com.jcclub.subject.infra.basic.service.ISubjectLabelService;
import com.jcclub.subject.infra.basic.service.ISubjectMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    private final ISubjectCategoryService subjectCategoryService;

    private final ISubjectMappingService subjectMappingService;

    private final ISubjectLabelService subjectLabelService;

    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        Integer count = subjectCategoryService.lambdaQuery()
                .eq(SubjectCategory::getCategoryName, subjectCategoryBO.getCategoryName())
                .eq(SubjectCategory::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(count > 0){
            throw new RuntimeException("该分类已存在");
        }
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
        boList.forEach(bo -> {
            Integer count = subjectMappingService.lambdaQuery().eq(SubjectMapping::getCategoryId, bo.getId()).count();
            bo.setCount(count);

        });

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
        Integer catCount = subjectCategoryService.lambdaQuery()
                .eq(SubjectCategory::getId, subjectCategoryBO.getId())
                .eq(SubjectCategory::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(catCount <= 0){
            throw new RuntimeException("该分类不存在");
        }
        Integer count = subjectMappingService.lambdaQuery()
                .eq(SubjectMapping::getCategoryId, subjectCategoryBO.getId())
                .eq(SubjectMapping::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if(count > 0){
            throw new RuntimeException("该分类下存在子分类或者题目，无法删除！");
        }

        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = subjectCategoryService.updateById(subjectCategory);
        return result;
    }


    @Override
    public List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO) {
        //查询当前分类下所有的子分类
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.lambdaQuery()
                .eq(SubjectCategory::getParentId, subjectCategoryBO.getId())
                .eq(SubjectCategory::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .list();
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.queryCategoryAndLabel.subjectCategoryList:{}",
                    JSON.toJSONString(subjectCategoryList));
        }
        List<SubjectCategoryBO> boList = SubjectCategoryConverter.INSTANCE.convertToCategoryBOList(subjectCategoryList);
        boList.forEach(bo -> {
            List<SubjectMapping> mappingList = subjectMappingService.lambdaQuery()
                    .eq(SubjectMapping::getCategoryId, bo.getId())
                    .eq(SubjectMapping::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                    .list();
            if(CollUtil.isEmpty(mappingList)){
                return;
            }
            List<Long> labelIds = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
            List<SubjectLabel> subjectLabels = subjectLabelService.listByIds(labelIds);
            List<SubjectLabelBO> labelBOList = SubjectLabelConverter.INSTANCE.convertToLabelBOList(subjectLabels);
            bo.setLabelBOList(labelBOList);
        });
        return boList;
    }
}
