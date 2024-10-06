package com.jcclub.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.jcclub.subject.application.convert.SubjectCategoryDTOConverter;
import com.jcclub.subject.application.convert.SubjectLabelDTOConverter;
import com.jcclub.subject.application.dto.SubjectCategoryDTO;
import com.jcclub.subject.application.dto.SubjectLabelDTO;
import com.jcclub.subject.common.entity.Result;
import com.jcclub.subject.domain.entity.SubjectCategoryBO;
import com.jcclub.subject.domain.service.SubjectCategoryDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/*
* 刷题分类模块接口
* */
@RestController
@RequestMapping("/subject/category")
@Slf4j
@RequiredArgsConstructor
public class SubjectCategoryController {

    private final SubjectCategoryDomainService subjectCategoryDomainService;


    /*
    *新增分类
    * */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO){
        log.info("新增分类信息：{}", subjectCategoryDTO);
        try {
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(subjectCategoryDTO.getCategoryName()), "分类名称不能为空");
            //Preconditions.checkNotNull(subjectCategoryDTO.getId(), "id不能为空");

            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDtoToCategoryBO(subjectCategoryDTO);
            subjectCategoryDomainService.add(subjectCategoryBO);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("新增分类信息失败", e);
            return Result.fail(e.getMessage());
        }
    }


    /*
    *
    * 查询一级分类
    * */
    @GetMapping("/queryPrimaryCategory")
    public Result<List<SubjectCategoryDTO>> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO){
        log.info("查询一级分类信息");

        try {

            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDtoToCategoryBO(subjectCategoryDTO);
            List<SubjectCategoryBO> boList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> dtoList = SubjectCategoryDTOConverter.INSTANCE.convertToCategoryDTOList(boList);
            return Result.ok(dtoList);
        }catch (Exception e){
            log.error("查询一级分类信息失败", e);
            return Result.fail("查询一级分类失败！");
        }
    }

    /*
    * 查询二级分类
    * */
    @GetMapping("/queryCategoryByPrimary")
    public Result<List<SubjectCategoryDTO>> queryCategoryByPrimary(@RequestBody SubjectCategoryDTO subjectCategoryDTO){
        log.info("查询一级分类下分类信息",subjectCategoryDTO);
        try {
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "id不能为空");

            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDtoToCategoryBO(subjectCategoryDTO);

            List<SubjectCategoryBO> boList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> dtoList = SubjectCategoryDTOConverter.INSTANCE.convertToCategoryDTOList(boList);
            return Result.ok(dtoList);
        }catch (Exception e){
            log.error("查询一级分类信息失败", e);
            return Result.fail("查询二级分类失败！");
        }
    }


    /*
    * 更新分类
    * */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectCategoryDTO subjectCategoryDTO){
        log.info("更新分类信息",subjectCategoryDTO);
        try {
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "id不能为空");

            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDtoToCategoryBO(subjectCategoryDTO);

            Boolean result = subjectCategoryDomainService.update(subjectCategoryBO);
            return Result.ok();
        }catch (Exception e){
            log.error("查询一级分类信息失败", e);
            return Result.fail("更新分类失败！");
        }
    }

    /**
     * @Description:删除分类
     * @data:[subjectCategoryDTO]
     * @return: com.jcclub.subject.common.entity.Result<java.lang.Boolean>
     * @Author: ZCY
     * @Date: 2024-09-29 09:09:41
     */

    @DeleteMapping("/delete")
    public Result<Boolean> deleteById(@RequestBody SubjectCategoryDTO subjectCategoryDTO){
        log.info("删除分类信息",subjectCategoryDTO);
        try {
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "id不能为空");

            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDtoToCategoryBO(subjectCategoryDTO);

            Boolean result = subjectCategoryDomainService.delete(subjectCategoryBO);
            return Result.ok();
        }catch (Exception e){
            log.error("查询一级分类信息失败", e);
            return Result.fail("删除分类失败！");
        }
    }


    /**
     * 查询分类及标签一次性
     */
    @GetMapping("/queryCategoryAndLabel")
    public Result<List<SubjectCategoryDTO>> queryCategoryAndLabel(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryCategoryAndLabel.dto:{}"
                        , JSON.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.
                    convertDtoToCategoryBO(subjectCategoryDTO);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategoryAndLabel(subjectCategoryBO);
            List<SubjectCategoryDTO> dtoList = new LinkedList<>();
            subjectCategoryBOList.forEach(bo -> {
                SubjectCategoryDTO dto = SubjectCategoryDTOConverter.INSTANCE.convertBoToCategoryDTO(bo);
                List<SubjectLabelDTO> labelDTOList = SubjectLabelDTOConverter.INSTANCE.convertToLabelDTOList(bo.getLabelBOList());
                dto.setLabelDTOList(labelDTOList);
                dtoList.add(dto);
            });
            return Result.ok(dtoList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error:{}", e.getMessage(), e);
            return Result.fail("查询失败");
        }
    }
}
