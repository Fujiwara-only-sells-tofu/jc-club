package com.jcclub.wx.handler;

import com.jcclub.wx.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName：ReceiveTextMsgHandler
 * @Author: gouteng
 * @Date: 2024/10/5 15:00
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class ReceiveTextMsgHandler implements WxChatMsgHandler {

    private static final String KEY_CODE = "验证码";

    private static final String LOGIN_PREFIX = "loginCode";

    private final RedisUtil redisUtil;

    @Override
    public WxChatMsgTypeEnum getMsgType() {
        return WxChatMsgTypeEnum.TEXT_MSG;
    }

    @Override
    public String dealMsg(Map<String, String> msgMap) {
        log.info("接收到用户文本消息");
        String content = msgMap.get("Content");
        if (!KEY_CODE.equals(content)) {
            return "";
        }
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");

        Random random = new Random();
        int code = random.nextInt(1000);
        //构建redisKEY
        String redisKey = redisUtil.buildKey(LOGIN_PREFIX, String.valueOf(code));
        redisUtil.setNx(redisKey, fromUserName, 5L, TimeUnit.MINUTES);
        String codeContent = "您当前的验证码是:" + code + ",五分钟之内有效！";
        String replayContent = "<xml>\n" +
                "  <ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[" + codeContent + "]]></Content>\n" +
                "</xml>";
        return replayContent;
    }
}
