package com.jcclub.subject.domain.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jcclub.subject.common.entity.PageResult;
import com.jcclub.subject.common.enums.IsDeletedFlagEnum;
import com.jcclub.subject.common.enums.SubjectLikedStatusEnum;
import com.jcclub.subject.common.utils.LoginUtil;
import com.jcclub.subject.domain.convert.SubjectLikedBOConverter;
import com.jcclub.subject.domain.entity.SubjectLikedBO;
import com.jcclub.subject.domain.entity.SubjectLikedMessage;
import com.jcclub.subject.domain.redis.RedisUtil;
import com.jcclub.subject.domain.service.SubjectLikedDomainService;
import com.jcclub.subject.infra.basic.entity.SubjectInfo;
import com.jcclub.subject.infra.basic.entity.SubjectLiked;
import com.jcclub.subject.infra.basic.service.ISubjectInfoService;
import com.jcclub.subject.infra.basic.service.ISubjectLikedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 题目点赞表 领域service实现了
 *
 * @author ZCY
 * @since 2024-10-12 10:16:18
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectLikedDomainServiceImpl implements SubjectLikedDomainService {


    private final ISubjectLikedService subjectLikedService;

    private final ISubjectInfoService subjectInfoService;

    private final RedisUtil redisUtil;

    private final RocketMQTemplate rocketMQTemplate;

    private static final String SUBJECT_LIKED_KEY = "subject.liked";

    private static final String SUBJECT_LIKED_COUNT_KEY = "subject.liked.count";

    private static final String SUBJECT_LIKED_DETAIL_KEY = "subject.liked.detail";

    @Override
    public void add(SubjectLikedBO subjectLikedBO) {
        Long subjectId = subjectLikedBO.getSubjectId();
        String likeUserId = subjectLikedBO.getLikeUserId();
        Integer status = subjectLikedBO.getStatus();
     /*   String hashKey = buildSubjectLikedKey(subjectId.toString(), likeUserId);
        redisUtil.putHash(SUBJECT_LIKED_KEY, hashKey, status);*/


        SubjectLikedMessage subjectLikedMessage = new SubjectLikedMessage();
        subjectLikedMessage.setSubjectId(subjectId);
        subjectLikedMessage.setLikeUserId(likeUserId);
        subjectLikedMessage.setStatus(status);
        rocketMQTemplate.convertAndSend("subject-liked", JSON.toJSONString(subjectLikedMessage));


        String detailKey = SUBJECT_LIKED_DETAIL_KEY + "." + subjectId + "." + likeUserId;
        String countKey = SUBJECT_LIKED_COUNT_KEY + "." + subjectId;
        if (SubjectLikedStatusEnum.LIKED.getCode() == status) {
            redisUtil.increment(countKey, 1);
            redisUtil.set(detailKey, "1");
        } else {
            Integer count = redisUtil.getInt(countKey);
            if (Objects.isNull(count) || count <= 0) {
                return;
            }
            redisUtil.increment(countKey, -1);
            redisUtil.del(detailKey);
        }

    }

    @Override
    public Boolean isLiked(String subjectId, String userId) {
        String detailKey = SUBJECT_LIKED_DETAIL_KEY + "." + subjectId + "." + userId;
        return redisUtil.exist(detailKey);
    }

    @Override
    public Integer getLikedCount(String subjectId) {
        String countKey = SUBJECT_LIKED_COUNT_KEY + "." + subjectId;
        Integer count = redisUtil.getInt(countKey);
        if (Objects.isNull(count) || count <= 0) {
            return 0;
        }
        return count;
    }

    //构建RedisKey方法
    private String buildSubjectLikedKey(String subjectId, String userId) {
        return subjectId + ":" + userId;
    }

    @Override
    public Boolean update(SubjectLikedBO subjectLikedBO) {
        SubjectLiked subjectLiked = SubjectLikedBOConverter.INSTANCE.convertBOToEntity(subjectLikedBO);
        return subjectLikedService.updateById(subjectLiked);
    }

    @Override
    public Boolean delete(SubjectLikedBO subjectLikedBO) {
        SubjectLiked subjectLiked = new SubjectLiked();
        subjectLiked.setId(subjectLikedBO.getId());
        subjectLiked.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        return subjectLikedService.updateById(subjectLiked);
    }


    @Override
    public void syncLikedData() {
        Map<Object, Object> subjectLikedMap = redisUtil.getHashAndDelete(SUBJECT_LIKED_KEY);
        if (log.isInfoEnabled()) {
            log.info("syncLikedData.subjectLikedMap:{}", JSON.toJSONString(subjectLikedMap));
        }
        if (MapUtils.isEmpty(subjectLikedMap)) {
            return;
        }
        //不为空同步到数据库中
        List<SubjectLiked> subjectLikedList = new LinkedList<>();
        subjectLikedMap.forEach((key, val) -> {
            SubjectLiked subjectLiked = new SubjectLiked();
            String[] keyArr = key.toString().split(":");
            String subjectId = keyArr[0];
            String likedUser = keyArr[1];
            subjectLiked.setSubjectId(Long.valueOf(subjectId));
            subjectLiked.setLikeUserId(likedUser);
            subjectLiked.setStatus(Integer.valueOf(val.toString()));
            subjectLiked.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            subjectLikedList.add(subjectLiked);
        });
        if (CollUtil.isEmpty(subjectLikedList)) {
            return;
        }
            subjectLikedService.batchInsertOrUpdate(subjectLikedList);
    }


    @Override
    public PageResult<SubjectLikedBO> getSubjectLikedPage(SubjectLikedBO subjectLikedBO) {
        PageResult<SubjectLikedBO> pageResult = new PageResult<>();
        pageResult.setPageNo(subjectLikedBO.getPageNo());
        pageResult.setPageSize(subjectLikedBO.getPageSize());
        Page<SubjectLiked> page = new Page<>(subjectLikedBO.getPageNo(), subjectLikedBO.getPageSize());
        SubjectLiked subjectLiked = SubjectLikedBOConverter.INSTANCE.convertBOToEntity(subjectLikedBO);
        subjectLiked.setLikeUserId(LoginUtil.getLoginId());
        Page<SubjectLiked> result = subjectLikedService.lambdaQuery()
                .eq(SubjectLiked::getLikeUserId, subjectLiked.getLikeUserId())
                .eq(SubjectLiked::getStatus, 1)
                .page(page);
        List<SubjectLiked> records = result.getRecords();
        if (CollUtil.isEmpty(records)) {
            return pageResult;
        }
        List<SubjectLikedBO> subjectLikedBOS = SubjectLikedBOConverter.INSTANCE.convertListInfoToBO(records);
        subjectLikedBOS.forEach(info -> {
            SubjectInfo subjectInfo = subjectInfoService.getById(info.getSubjectId());
            info.setSubjectName(subjectInfo.getSubjectName());
        });
        pageResult.setRecords(subjectLikedBOS);
        pageResult.setTotal(records.size());
        return pageResult;
    }

    @Override
    public void syncLikedByMsg(SubjectLikedBO subjectLikedBO) {
        List<SubjectLiked> subjectLikedList = new LinkedList<>();
        SubjectLiked subjectLiked = new SubjectLiked();
        subjectLiked.setSubjectId(Long.valueOf(subjectLikedBO.getSubjectId()));
        subjectLiked.setLikeUserId(subjectLikedBO.getLikeUserId());
        subjectLiked.setStatus(subjectLikedBO.getStatus());
        subjectLiked.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectLikedList.add(subjectLiked);

        subjectLikedService.batchInsertOrUpdate(subjectLikedList);

    }
}
