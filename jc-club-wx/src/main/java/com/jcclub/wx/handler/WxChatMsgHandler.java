package com.jcclub.wx.handler;

import java.util.Map;

/**
 * @ClassName：WxChatMsgHandler
 * @Author: gouteng
 * @Date: 2024/10/5 14:58
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

public interface WxChatMsgHandler {

    WxChatMsgTypeEnum getMsgType();

    String dealMsg(Map<String, String> msgMap);
}
