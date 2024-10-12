package com.jcclub.subject.domain.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.domain.convert.SubjectCategoryConverter;
import com.jcclub.subject.domain.convert.SubjectLabelConverter;
import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import com.jcclub.subject.domain.entity.SubjectLabelBO;
import com.jcclub.subject.domain.service.SubjectCategoryDomainService;
import com.jcclub.subject.domain.utils.CacheUtil;
import com.jcclub.subject.infra.basic.entity.SubjectCategory;
import com.jcclub.subject.infra.basic.entity.SubjectLabel;
import com.jcclub.subject.infra.basic.entity.SubjectMapping;
import com.jcclub.subject.infra.basic.service.ISubjectCategoryService;
import com.jcclub.subject.infra.basic.service.ISubjectLabelService;
import com.jcclub.subject.infra.basic.service.ISubjectMappingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    private final ISubjectCategoryService subjectCategoryService;

    private final ISubjectMappingService subjectMappingService;

    private final ISubjectLabelService subjectLabelService;

    private final ThreadPoolExecutor labelThreadPool;

    private final CacheUtil cacheUtil;


    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        Integer count = subjectCategoryService.lambdaQuery()
                .eq(SubjectCategory::getCategoryName, subjectCategoryBO.getCategoryName())
                .eq(SubjectCategory::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if (count > 0) {
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
                .eq(subjectCategory.getParentId() != null, SubjectCategory::getParentId, subjectCategory.getParentId())
                .eq(subjectCategory.getCategoryType() != null, SubjectCategory::getCategoryType, subjectCategory.getCategoryType())
                .eq(SubjectCategory::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .list();
        if (CollUtil.isEmpty(subjectCategories)) {
            throw new RuntimeException("查询大类失败");
        }
        List<SubjectCategoryBO> boList = SubjectCategoryConverter.INSTANCE.convertToCategoryBOList(subjectCategories);
        boList.forEach(bo -> {
            //查询当前分类下所有的子分类
            List<Long> allIdList = new ArrayList<>();
            List<SubjectCategory> allList = subjectCategoryService.lambdaQuery()
                    .eq(SubjectCategory::getParentId, bo.getId())
                    .eq(SubjectCategory::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                    .list();
            //将自身id加入到allIdList中
            if (CollUtil.isNotEmpty(allList)) {
                allIdList = allList.stream().map(SubjectCategory::getId).collect(Collectors.toList());
            }
            allIdList.add(bo.getId());
            //再根据分类以及子分类查询出所有的题目数量
            Integer count = subjectMappingService.lambdaQuery().in(SubjectMapping::getCategoryId, allIdList).count();
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
        if (catCount <= 0) {
            throw new RuntimeException("该分类不存在");
        }
        Integer count = subjectMappingService.lambdaQuery()
                .eq(SubjectMapping::getCategoryId, subjectCategoryBO.getId())
                .eq(SubjectMapping::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .count();
        if (count > 0) {
            throw new RuntimeException("该分类下存在子分类或者题目，无法删除！");
        }

        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        boolean result = subjectCategoryService.updateById(subjectCategory);
        return result;
    }


    @Override
    @SneakyThrows
    public List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO) {

        Long id = subjectCategoryBO.getId();
        //构建本地缓存的key
        String cacheKey = "categoryAndLabel." + subjectCategoryBO.getId();
        List<SubjectCategoryBO> subjectCategoryBOS = cacheUtil.getResult(cacheKey, SubjectCategoryBO.class, (key) -> getSubjectCategoryBOS(id));
        return subjectCategoryBOS;
    }

    private List<SubjectCategoryBO> getSubjectCategoryBOS(Long categoryId) {
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.lambdaQuery()
                .eq(SubjectCategory::getParentId, categoryId)
                .eq(SubjectCategory::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .list();
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.queryCategoryAndLabel.subjectCategoryList:{}",
                    JSON.toJSONString(subjectCategoryList));
        }
        List<SubjectCategoryBO> boList = SubjectCategoryConverter.INSTANCE.convertToCategoryBOList(subjectCategoryList);
        Map<Long, List<SubjectLabelBO>> map = new HashMap<>();
        List<CompletableFuture<Map<Long, List<SubjectLabelBO>>>> completableFutureList = boList.stream().map(bo ->
                        CompletableFuture.supplyAsync(() -> getLabelBOList(bo), labelThreadPool))
                .collect(Collectors.toList());
        completableFutureList.forEach(future -> {
            try {
                Map<Long, List<SubjectLabelBO>> resultMap = future.get();
                map.putAll(resultMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        boList.forEach(bo -> {
            bo.setLabelBOList(map.get(bo.getId()));
        });

        return boList;
    }

    private Map<Long, List<SubjectLabelBO>> getLabelBOList(SubjectCategoryBO bo) {
        if (log.isInfoEnabled()) {
            log.info("getLabelBOList :{}", JSON.toJSONString(bo));
        }
        log.info("当前线程是：{}", Thread.currentThread().getName());

        Map<Long, List<SubjectLabelBO>> labelMap = new HashMap<>();

        List<SubjectMapping> mappingList = subjectMappingService.lambdaQuery()
                .eq(SubjectMapping::getCategoryId, bo.getId())
                .eq(SubjectMapping::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .list();
        if (CollUtil.isEmpty(mappingList)) {
            return null;
        }
        List<Long> labelIds = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> subjectLabels = subjectLabelService.listByIds(labelIds);
        List<SubjectLabelBO> labelBOList = SubjectLabelConverter.INSTANCE.convertToLabelBOList(subjectLabels);

        labelMap.put(bo.getId(), labelBOList);
        return labelMap;
    }
}
