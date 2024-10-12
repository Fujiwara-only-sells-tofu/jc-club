package com.jcclub.subject.domain.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @ClassName：CacheUtil
 * @Author: gouteng
 * @Date: 2024/10/10 9:26
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Component
public class CacheUtil<K,V> {


    private Cache<String, String> localCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    .build();


    public List<V> getResult(String cacheKey, Class<V> clazz,
                             Function<String, List<V>> function) {
        List<V> resultList = new ArrayList<>();
        //查缓存
        String content = localCache.getIfPresent(cacheKey);
        if (StringUtils.isNotBlank(content)) {
            //不为空解析字符串放入集合中
            resultList = JSON.parseArray(content, clazz);
        } else {
            //为空查询数据库
            resultList = function.apply(cacheKey);
            if (!CollectionUtils.isEmpty(resultList)) {
                //不为空放入缓存
                localCache.put(cacheKey, JSON.toJSONString(resultList));
            }
        }
        return resultList;
    }

    public Map<K, V> getMapResult(String cacheKey, Class<V> clazz,
                                  Function<String, Map<K, V>> function) {
        return new HashMap<>();
    }


}
