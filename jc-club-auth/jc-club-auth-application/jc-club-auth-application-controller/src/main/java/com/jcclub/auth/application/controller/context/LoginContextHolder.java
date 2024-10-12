package com.jcclub.auth.application.controller.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName：LoginContextHolder
 * @Author: gouteng
 * @Date: 2024/10/9 20:03
 * @Description: 登录上下文对象
 */

public class LoginContextHolder {

    //在线程中不能传递
    //private static final ThreadLocal<Map<String,Object>> THREAD_LOCAL =new ThreadLocal<>();

    private static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL =new InheritableThreadLocal<>();

    public static void set(String key, Object val){
        Map<String,Object> map = getThreadLocalMap();
        map.put(key, val);
    }

    public static Object get(String key){
        Map<String,Object> threadLocalMap = getThreadLocalMap();
        return threadLocalMap.get(key);
    }


    public static void remove(){
        THREAD_LOCAL.remove();
    }

    public static Map<String,Object> getThreadLocalMap(){
        Map<String, Object> map = THREAD_LOCAL.get();
        if(Objects.isNull(map)){
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }


    public static String getLoginId(){
        return getThreadLocalMap().get("loginId").toString();
    }
}
