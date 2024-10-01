package com.jcclub.subject.application.controller;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Preconditions;
import com.jcclub.subject.application.convert.SubjectAnswerDTOConverter;
import com.jcclub.subject.application.convert.SubjectInfoDTOConverter;
import com.jcclub.subject.application.dto.SubjectInfoDTO;
import com.jcclub.subject.common.entity.PageResult;
import com.jcclub.subject.common.entity.Result;
import com.jcclub.subject.common.entity.SubjectPageQuery;
import com.jcclub.subject.domain.entity.SubjectAnswerBO;
import com.jcclub.subject.domain.entity.SubjectInfoBO;
import com.jcclub.subject.domain.service.SubjectInfoDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description:题目相关接口
 * @data:* @param null
 * @return:
 * @return: null
 * @Author: ZCY
 * @Date: 2024-09-29 13:48:15
 */

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {

    private final SubjectInfoDomainService subjectInfoDomainService;

    /**
     * @Description:新增题目
     * @data:[subjectInfoDTO]
     * @return: com.jcclub.subject.common.entity.Result<java.lang.Boolean>
     * @Author: ZCY
     * @Date: 2024-09-29 16:30:07
     */

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        log.info("新增题目信息：{}", subjectInfoDTO);
        try {
            Preconditions.checkArgument(!StringUtils.isBlank(subjectInfoDTO.getSubjectName()), "题目名称不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectType(), "题目类型不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectDifficult(), "题目难度不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectScore(), "题目分数不能为空");
            Preconditions.checkArgument(!CollUtil.isEmpty(subjectInfoDTO.getCategoryIds()),"题目分类id不能为空");
            Preconditions.checkArgument(!CollUtil.isEmpty(subjectInfoDTO.getLabelIds()),"题目标签id不能为空");

            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDtoToInfoBO(subjectInfoDTO);
            List<SubjectAnswerBO> subjectAnswerBOS = SubjectAnswerDTOConverter.INSTANCE.convertDtoToAnswerBOList(subjectInfoDTO.getOptionList());
            subjectInfoBO.setOptionList(subjectAnswerBOS);
            subjectInfoDomainService.add(subjectInfoBO);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("新增分类信息失败", e);
            return Result.fail(e.getMessage());
        }
    }
    /**
     * @Description:查询题目列表
     * @data:[subjectInfoDTO]
     * @return: com.jcclub.subject.common.entity.Result<com.jcclub.subject.common.entity.PageResult<com.jcclub.subject.domain.entity.SubjectInfoBO>>
     * @Author: ZCY
     * @Date: 2024-10-01 14:28:59
     */

    @GetMapping("/getSubjectPage")
    public Result<PageResult<SubjectInfoDTO>> getSubjectPage(@RequestBody SubjectPageQuery query) {
        log.info("分页信息：{}", query);
        //DOTO 更换分页对象，重写分页查询
        try {
            Preconditions.checkNotNull(query.getCategoryId(), "分类Id不能为空");
            Preconditions.checkNotNull(query.getLabelId(), "标签id不能为空");

            //SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDtoToInfoBO(subjectInfoDTO);
            //log.info("转换之后 {}",subjectInfoBO);
            PageResult<SubjectInfoBO> result = subjectInfoDomainService.getSubjectPage(query);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("查询分页信息失败", e);
            return Result.fail(e.getMessage());
        }
    }


    /**
     * @Description:查询题目信息
     * @data:[subjectInfoDTO]
     * @return: com.jcclub.subject.common.entity.Result<com.jcclub.subject.common.entity.PageResult<com.jcclub.subject.domain.entity.SubjectInfoBO>>
     * @Author: ZCY
     * @Date: 2024-10-01 14:29:14
     */

    @GetMapping("/querySubjectInfo")
    public Result<SubjectInfoDTO> querySubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        log.info("查询的信息：{}", subjectInfoDTO);
        try {
            Preconditions.checkNotNull(subjectInfoDTO.getId(), "Id不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDtoToInfoBO(subjectInfoDTO);
            log.info("转换之后 {}",subjectInfoBO);
            SubjectInfoBO boResult =  subjectInfoDomainService.querySubjectInfo(subjectInfoBO);
            SubjectInfoDTO dtoResult = SubjectInfoDTOConverter.INSTANCE.convertBOToInfoDTO(boResult);
            return Result.ok(dtoResult);
        } catch (Exception e) {
            log.error("查询信息失败", e);
            return Result.fail(e.getMessage());
        }
    }

}
