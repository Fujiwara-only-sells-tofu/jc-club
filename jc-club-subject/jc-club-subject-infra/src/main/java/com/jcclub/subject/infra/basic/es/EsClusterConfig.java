package com.jcclub.subject.infra.basic.es;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName：EsClusterConfig
 * @Author: gouteng
 * @Date: 2024/10/10 13:48
 * @Description: es集群类
 */

@Data
public class EsClusterConfig implements Serializable {

    /**
     * 集群名称
     */

    private String name;
    /**
     * 集群节点
     */
    private String nodes;
}
