package com.jcclub.wx.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：WxChatMsgFactory
 * @Author: gouteng
 * @Date: 2024/10/5 15:02
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Component
@RequiredArgsConstructor
public class WxChatMsgFactory implements InitializingBean {

    private final List<WxChatMsgHandler> wxChatMsgHandlerList;

    Map<WxChatMsgTypeEnum, WxChatMsgHandler> handlerMap = new HashMap<>();

    public WxChatMsgHandler getHandlerByMsgType(String msgType) {
        WxChatMsgTypeEnum wxChatMsgTypeEnum = WxChatMsgTypeEnum.getByMsgType(msgType);
        return handlerMap.get(wxChatMsgTypeEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (WxChatMsgHandler wxChatMsgHandler : wxChatMsgHandlerList) {
            handlerMap.put(wxChatMsgHandler.getMsgType(), wxChatMsgHandler);
        }
    }

}
