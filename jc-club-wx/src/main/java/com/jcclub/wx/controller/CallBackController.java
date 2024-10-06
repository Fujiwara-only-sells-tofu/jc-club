package com.jcclub.wx.controller;

import com.jcclub.wx.handler.WxChatMsgFactory;
import com.jcclub.wx.handler.WxChatMsgHandler;
import com.jcclub.wx.utils.MessageUtil;
import com.jcclub.wx.utils.SHA1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @ClassName：CallBackController
 * @Author: gouteng
 * @Date: 2024/10/5 13:52
 * @Description: 必须描述类做什么事情, 实现什么功能
 */

@RestController
@Slf4j
@RequiredArgsConstructor
public class CallBackController {


    private static final String token = "wuyanzu";

    private final WxChatMsgFactory wxChatMsgFactory;

    @RequestMapping("/test")
    public String test() {
        return "hello";
    }


    /**
     * @Description: 回调信息校验
     * @data:[signature, timestamp, nonce, echostr]
     * @return: java.lang.String
     * @Author: ZCY
     * @Date: 2024-10-05 14:00:29
     */

    @GetMapping("callback")
    public String callback(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {
        log.info("get验签请求参数：signature:{}，timestamp:{}，nonce:{}，echostr:{}",
                signature, timestamp, nonce, echostr);
        String shaStr = SHA1.getSHA1(token, timestamp, nonce, "");
        if (shaStr.equals(signature)) {
            return echostr;
        }
        return "unknown";
    }


    @PostMapping(value = "callback", produces = "application/xml;charset=UTF-8")
    public String callback(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(value = "msg_signature", required = false) String msgSignature) {
        log.info("接收到微信消息：requestBody：{}", requestBody);
        Map<String, String> msgMap = MessageUtil.parseXml(requestBody);
        String msgType = msgMap.get("MsgType");
        String event = msgMap.get("Event") == null ? "" : msgMap.get("Event");
        log.info("接收到微信事件：msgType：{}，event：{}", msgType, event);
        //创建工厂的key
        StringBuilder sb = new StringBuilder();
        sb.append(msgType);
        if (!StringUtils.isEmpty(event)) {
            sb.append(".");
            sb.append(event);
        }
        String msgTypeKey = sb.toString();
        WxChatMsgHandler handler = wxChatMsgFactory.getHandlerByMsgType(msgTypeKey);
        if(Objects.isNull(handler)){
            return "unknown";
        }
        String replyContent = handler.dealMsg(msgMap);
        log.info("回复微信消息：replyContent：{}", replyContent);
        return replyContent;
    }
}
