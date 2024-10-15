package com.jcclub.subject.application.mq;

import com.alibaba.fastjson.JSON;
import com.jcclub.subject.domain.entity.SubjectLikedBO;
import com.jcclub.subject.domain.service.SubjectLikedDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName：TestConsumer
 * @Author: 张辰逸
 * @Date: 2024/10/15 15:47
 * @Description: 测试mq消费者
 */

@Component
@RocketMQMessageListener(topic = "subject-liked", consumerGroup = "subject-liked-consumer")
@Slf4j
@RequiredArgsConstructor
public class SubjectLikedConsumer implements RocketMQListener<String> {


    private final SubjectLikedDomainService subjectLikedDomainService;

    @Override
    public void onMessage(String s) {
        System.out.println("接收到点赞的mq消息:{}" + s);
        //将消息从json转换为对象
        SubjectLikedBO bo = JSON.parseObject(s, SubjectLikedBO.class);
        subjectLikedDomainService.syncLikedByMsg(bo);
    }
}
