package com.jcclub.practice.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jcclub.practice.api.req.*;
import com.jcclub.practice.api.vo.RankVO;
import com.jcclub.practice.api.vo.ReportVO;
import com.jcclub.practice.api.vo.ScoreDetailVO;
import com.jcclub.practice.api.vo.SubjectDetailVO;
import com.jcclub.practice.server.entity.po.PracticeDetailPO;

import java.util.List;

/**
 * <p>
 * 练习详情表 服务类
 * </p>
 *
 * @author 张辰逸
 * @since 2024-10-13
 */
public interface IPracticeDetailService extends IService<PracticeDetailPO> {



    /**
     * 练习提交题目
     */
    Boolean submitSubject(SubmitSubjectDetailReq req);

    /**
     * 提交练题情况
     */
    Boolean submit(SubmitPracticeDetailReq req);

    /**
     * 每题得分详情
     */
    List<ScoreDetailVO> getScoreDetail(GetScoreDetailReq req);

    /**
     * 获得答案详情
     */
    SubjectDetailVO getSubjectDetail(GetSubjectDetailReq req);

    /**
     * 答案解析-评估报告
     */
    ReportVO getReport(GetReportReq req);

    /**
     * 练习榜
     */
    List<RankVO> getPracticeRankList();

    /**
     * 放弃练习
     */
    Boolean giveUp(Long practiceId);
}
