package com.jcclub.subject.infra.basic.es;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName：EsConfigProperties
 * @Author: gouteng
 * @Date: 2024/10/10 13:50
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Component
@ConfigurationProperties(prefix = "es.cluster")
public class EsConfigProperties {

    private List<EsClusterConfig> esConfigs = new ArrayList<>();

    public List<EsClusterConfig> getEsConfigs() {
        return esConfigs;
    }

    public void setEsConfigs(List<EsClusterConfig> esConfigs) {
        this.esConfigs = esConfigs;
    }
}
