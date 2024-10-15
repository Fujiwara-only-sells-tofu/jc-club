package com.jcclub.subject.domain.service;


import com.jcclub.subject.common.entity.PageResult;
import com.jcclub.subject.domain.entity.SubjectLikedBO;

/**
 * 题目点赞表 领域service
 *
 * @author ZCY
 * @since 2024-10-12 10:16:18
 */
public interface SubjectLikedDomainService {

    /**
     * 添加 题目点赞表 信息
     */
    void add(SubjectLikedBO subjectLikedBO);


    /**
     * 获取当前用户是否点过赞
     */
    Boolean isLiked(String subjectId, String userId);


    /**
     * 获取当前题目被点赞数量
     */
    Integer getLikedCount(String subjectId);

    /**
     * 更新 题目点赞表 信息
     */
    Boolean update(SubjectLikedBO subjectLikedBO);

    /**
     * 删除 题目点赞表 信息
     */
    Boolean delete(SubjectLikedBO subjectLikedBO);
    /**
     * 同步点赞数据
     */
    void syncLikedData();

    /**
     * @Description: 分页查询题目点赞信息
     * @data:[subjectLikedBO]
     * @return: com.jcclub.subject.common.entity.PageResult<com.jcclub.subject.domain.entity.SubjectLikedBO>
     * @Author: ZCY
     * @Date: 2024-10-12 16:30:13
     */

    PageResult<SubjectLikedBO> getSubjectLikedPage(SubjectLikedBO subjectLikedBO);

    /**
    *@Title: syncLikedByMsg
    * @Author: 张辰逸
    * @Date: 2024-10-15 16:26:30
    * @Params: [bo]
    * @Return: void
    * @Description: mq消息同步点赞
     */

    void syncLikedByMsg(SubjectLikedBO bo);
}
