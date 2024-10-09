package com.jcclub.subject.domain.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jcclub.subject.common.entity.PageResult;
import com.jcclub.subject.common.entity.SubjectPageQuery;
import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.domain.convert.SubjectInfoConverter;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.domain.entity.SubjectOptionBO;
import com.jcclub.subject.domain.handler.subject.SubjectTypeHandler;
import com.jcclub.subject.domain.handler.subject.SubjectTypeHandlerFactory;
import com.jcclub.subject.domain.service.SubjectInfoDomainService;
import com.jcclub.subject.infra.basic.entity.SubjectInfo;
import com.jcclub.subject.infra.basic.entity.SubjectLabel;
import com.jcclub.subject.infra.basic.entity.SubjectMapping;
import com.jcclub.subject.infra.basic.service.ISubjectInfoService;
import com.jcclub.subject.infra.basic.service.ISubjectLabelService;
import com.jcclub.subject.infra.basic.service.ISubjectMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {

    private final ISubjectInfoService subjectInfoService;

    private final SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    private final ISubjectMappingService subjectMappingService;

    private final ISubjectLabelService subjectLabelService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SubjectInfoBO subjectInfoBO) {
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBoToInfo(subjectInfoBO);
        subjectInfoService.save(subjectInfo);
        subjectInfoBO.setId(subjectInfo.getId());
        //使用工厂进行根据类型自动映射处理
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
        handler.add(subjectInfoBO);

        Long subjectInfoId = subjectInfo.getId();

        //要将映射关系添加到mapping表中
        List<Integer> categoryIds = subjectInfoBO.getCategoryIds();
        List<Integer> labelIds = subjectInfoBO.getLabelIds();
        LinkedList<SubjectMapping> subjectMappings = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfoId);
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setLabelId(Long.valueOf(labelId));
                subjectMappings.add(subjectMapping);
            });
        });
        subjectMappingService.saveBatch(subjectMappings);
    }


    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectPageQuery query) {
        PageResult<SubjectInfoBO> pageResult = new PageResult<>();
        pageResult.setPageSize(query.getPageSize());
        pageResult.setPageNo(query.getPageNo());

        // 先查询符合条件的所有 SubjectMapping 记录
        List<SubjectMapping> mappingList = subjectMappingService.lambdaQuery()
                .eq(SubjectMapping::getCategoryId, query.getCategoryId())
                .eq(SubjectMapping::getLabelId, query.getLabelId())
                .list();

        // 获取所有符合条件的 SubjectId
        Set<Long> subjectIds = mappingList.stream()
                .map(SubjectMapping::getSubjectId)
                .collect(Collectors.toSet());

        // 查询 info 表中所有符合条件的 SubjectInfo
        List<SubjectInfo> subjectInfos = subjectInfoService.listByIds(subjectIds);

        List<SubjectInfo> filteredInfos = subjectInfos;
        if(query.getSubjectDifficult()!=null){
            // 进行难度过滤
            filteredInfos = subjectInfos.stream()
                    .filter(subjectInfo -> subjectInfo.getSubjectDifficult().equals(query.getSubjectDifficult()))
                    .collect(Collectors.toList());
        }

        // 根据过滤后的数据进行分页
        int totalSize = filteredInfos.size();
        int pageNo = query.getPageNo();
        int pageSize = query.getPageSize();

        // 计算分页开始和结束索引
        int start = (pageNo - 1) * pageSize;
        int end = Math.min(start + pageSize, totalSize);

        // 设置分页结果
        List<SubjectInfoBO> boList = SubjectInfoConverter.INSTANCE.convertToInfoBOList(filteredInfos.subList(start, end));
        pageResult.setTotal(totalSize);
        pageResult.setResult(boList);

        return pageResult;
    }


    @Override
    public SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO) {
        SubjectInfo info = subjectInfoService.getById(subjectInfoBO.getId());
        if (info == null) {
            throw new RuntimeException("查询不到该题目");
        }
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(info.getSubjectType());
        SubjectOptionBO optionBO = handler.query(info.getId().intValue());
        SubjectInfoBO bo = SubjectInfoConverter.INSTANCE.convertOptionAndInfoToBO(optionBO, info);
        List<String> labelNameList = new ArrayList<>();
        List<SubjectMapping> mappingList = subjectMappingService.lambdaQuery()
                .eq(SubjectMapping::getSubjectId, info.getId())
                .eq(SubjectMapping::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .list();
        if (CollUtil.isEmpty(mappingList)) {
            return bo;
        }
        List<Long> labelList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> subjectLabels = subjectLabelService.listByIds(labelList);
        if (CollUtil.isEmpty(subjectLabels)) {
            return bo;
        }
        subjectLabels.stream().map(SubjectLabel::getLabelName).forEach(labelNameList::add);
        bo.setLabelName(labelNameList);
        log.info("返回的实体：",bo);
        return bo;
    }
}
