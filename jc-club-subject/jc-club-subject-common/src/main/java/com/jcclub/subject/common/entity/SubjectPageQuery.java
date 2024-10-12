package com.jcclub.subject.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName：SubjectPageQuery
 * @Author: gouteng
 * @Date: 2024/10/1 20:13
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Data
public class SubjectPageQuery extends PageInfo implements Serializable {

    /**
     * 标签id
     */

    private Integer labelId;

    /**
     * 分类id
     */

    private Integer categoryId;


    /**
     * 难度等级
     */

    private Integer subjectDifficult;


    /**
     * 关键词
     */

    private String keyWord;
}
