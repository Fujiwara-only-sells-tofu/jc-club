package com.jcclub.subject.common.utils;


import com.jcclub.subject.common.context.LoginContextHolder;

/**
 * @ClassName：LoginUtil
 * @Author: gouteng
 * @Date: 2024/10/9 20:18
 * @Description: 登录工具类
 */

public class LoginUtil {
    public static String getLoginId(){
        return LoginContextHolder.getLoginId();
    }
}
