package com.jcclub.subject.application.mq;

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
@RocketMQMessageListener(topic = "test_topic", consumerGroup = "test_group")
@Slf4j
public class TestConsumer implements RocketMQListener<String> {


    @Override
    public void onMessage(String s) {
        log.info("接收到mq消息:{}", s);
    }
}
