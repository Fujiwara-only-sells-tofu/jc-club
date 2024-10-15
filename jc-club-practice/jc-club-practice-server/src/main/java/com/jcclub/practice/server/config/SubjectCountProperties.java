package com.jcclub.practice.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName：SubjectCountProperties
 * @Author: 张辰逸
 * @Date: 2024/10/15 9:12
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Data
@Component
@ConfigurationProperties(prefix = "subject.count")
public class SubjectCountProperties {

    // 单选题数量
    private Integer radioSubjectCount;
    // 多选题数量
    private Integer multipleSubjectCount;
    // 判断题数量
    private Integer judgeSubjectCount;
    // 总题目数量
    private Integer totalSubjectCount;
}
