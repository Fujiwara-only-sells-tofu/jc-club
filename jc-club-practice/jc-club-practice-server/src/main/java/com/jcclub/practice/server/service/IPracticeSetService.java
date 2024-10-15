package com.jcclub.practice.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jcclub.practice.api.common.PageResult;
import com.jcclub.practice.api.req.GetPracticeSubjectsReq;
import com.jcclub.practice.api.req.GetUnCompletePracticeReq;
import com.jcclub.practice.api.vo.*;
import com.jcclub.practice.server.entity.dto.PracticeSetDTO;
import com.jcclub.practice.server.entity.dto.PracticeSubjectDTO;
import com.jcclub.practice.server.entity.po.PracticeSetPO;

import java.util.List;

/**
 * <p>
 * 套题信息表 服务类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-13
 */
public interface IPracticeSetService extends IService<PracticeSetPO> {

    /**
    *@Title: getSpecialPracticeContent
    * @Author: 张辰逸
    * @Date: 2024-10-13 14:07:18
    * @Params: []
    * @Return: List<SpecialPracticeVO>
    * @Description: 获取专项练习内容
     */

    List<SpecialPracticeVO> getSpecialPracticeContent();

    /**
    *@Title: addPractice
    * @Author: 张辰逸
    * @Date: 2024-10-15 08:57:07
    * @Params: [dto]
    * @Return: PracticeSetVO
    * @Description: 开始练习
     */

    PracticeSetVO addPractice(PracticeSubjectDTO dto);

    /**
     * 获取练习题
     */
    PracticeSubjectListVO getSubjects(GetPracticeSubjectsReq req);

    /**
     * 获取题目
     */
    PracticeSubjectVO getPracticeSubject(PracticeSubjectDTO dto);

    /**
     * 获取模拟套题内容
     */
    PageResult<PracticeSetVO> getPreSetContent(PracticeSetDTO dto);

    /**
     * 获取未完成练习内容
     */
    PageResult<UnCompletePracticeSetVO> getUnCompletePractice(GetUnCompletePracticeReq req);
}
