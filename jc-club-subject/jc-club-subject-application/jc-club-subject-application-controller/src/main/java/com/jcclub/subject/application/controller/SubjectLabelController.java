package com.jcclub.subject.application.controller;

import com.google.common.base.Preconditions;
import com.jcclub.subject.application.convert.SubjectLabelDTOConverter;
import com.jcclub.subject.application.dto.SubjectLabelDTO;
import com.jcclub.subject.common.entity.Result;
import com.jcclub.subject.domain.entity.SubjectLabelBO;
import com.jcclub.subject.domain.service.SubjectLabelDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName：SubjectLabelController
 * @Author: gouteng
 * @Date: 2024/9/29 9:54
 * @Description: 题目标签相关接口
 */

@RestController
@RequestMapping("/subject/label")
@Slf4j
@RequiredArgsConstructor
public class SubjectLabelController {


    private final SubjectLabelDomainService subjectLabelDomainService;

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        log.info("subjectLabelDTO:{}", subjectLabelDTO);
        try {
            Preconditions.checkNotNull(subjectLabelDTO.getLabelName(), "标签名称不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDtoToLabelBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.add(subjectLabelBO);
            return Result.ok(result);
        }catch (Exception e){
            log.error("新增标签失败:{}", e.getMessage());
            return Result.fail("新增标签失败");
        }
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        log.info("subjectLabelDTO:{}", subjectLabelDTO);
        try {
            Preconditions.checkNotNull(subjectLabelDTO.getId(), "标签ID不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDtoToLabelBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.update(subjectLabelBO);
            return Result.ok(result);
        }catch (Exception e){
            log.error("更新标签失败:{}", e.getMessage());
            return Result.fail("更新标签失败");
        }
    }

    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        log.info("subjectLabelDTO:{}", subjectLabelDTO);
        try {
            Preconditions.checkNotNull(subjectLabelDTO.getId(), "标签ID不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDtoToLabelBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.delete(subjectLabelBO);
            return Result.ok(result);
        }catch (Exception e){
            log.error("删除标签失败:{}", e.getMessage());
            return Result.fail("删除标签失败");
        }
    }


    @PostMapping("/queryLabelByCategoryId")
    public Result<List<SubjectLabelDTO>> queryLabelByCategoryId(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        log.info("subjectLabelDTO:{}", subjectLabelDTO);
        try {
            Preconditions.checkNotNull(subjectLabelDTO.getCategoryId(), "分类ID不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDtoToLabelBO(subjectLabelDTO);
            List<SubjectLabelBO> boList= subjectLabelDomainService.queryLabelByCategoryId(subjectLabelBO);
            List<SubjectLabelDTO> dtoList = SubjectLabelDTOConverter.INSTANCE.convertToLabelDTOList(boList);
            return Result.ok(dtoList);
        }catch (Exception e){
            log.error("查询分类下标签失败:{}", e.getMessage());
            return Result.fail("查询分类下标签失败");
        }
    }

}
