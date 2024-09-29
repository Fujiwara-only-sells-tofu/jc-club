package com.jcclub.subject.application.controller;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Preconditions;
import com.jcclub.subject.application.convert.SubjectAnswerDTOConverter;
import com.jcclub.subject.application.convert.SubjectInfoDTOConverter;
import com.jcclub.subject.application.dto.SubjectInfoDTO;
import com.jcclub.subject.common.entity.Result;
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


}
