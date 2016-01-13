package org.singledog.wechat.sdk.handler.analyzer.robot;

import org.singledog.wechat.sdk.handler.analyzer.MessageAnalyzer;
import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.TextMessage;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.message.component.MessageComponent;
import org.singledog.wechat.sdk.message.reply.ReplyTextMessage;
import org.singledog.wechat.sdk.util.HttpUtil;
import org.singledog.wechat.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * this will be called last
 *
 * Created by adam on 16-1-4.
 */
//@Component
@Profile("enable-robot")
public class RobotTextAnalyzer implements MessageAnalyzer<TextMessage> {
    private static final Logger logger = LoggerFactory.getLogger(RobotTextAnalyzer.class);

    @Value("${wechat.sdk.robot.key}")
    private String robotKey;
    @Value("${wechat.sdk.robot.uri}")
    private String robotURI;

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public WeChatMessage analyze(TextMessage weChatMessage) {
        String info = weChatMessage.getContent();
        Map<String, String> params = new HashMap<>();
        params.put("key", robotKey);
        params.put("info", info);
        params.put("userid", weChatMessage.getFromUserName());

        String message = null;
        try {
            String json = HttpUtil.post(robotURI, HttpUtil.toParams(params));
            Map<String, Object> map = JsonUtil.toMap(json);
            int code = Integer.valueOf(String.valueOf(map.get("code")));
            Class clazz = null;
            try {
                RobotResults results = RobotResults.valueOf(code);
                if (results != null) {
                    clazz = results.resultClass();
                }
            } catch (IllegalArgumentException e) {
                clazz = RobotResults.text.resultClass();
            }

            if (clazz != null) {
                Object obj = JsonUtil.fromJson(json, clazz);
                if (obj != null) {
                    message = obj.toString();
                }
            }

            if (StringUtils.isEmpty(message)) {
                message = "Oops... 出错了...";
            }
        } catch (Exception e) {
            logger.error("error request robot response with data : {}", info);
            logger.error(e.getMessage(), e);
            message = "Oops... 出错了...";
        }

        logger.debug("found message : {} for request : {}", message, info);

        ReplyTextMessage replyTextMessage = new ReplyTextMessage(weChatMessage);
        replyTextMessage.setContent(message);
        return replyTextMessage;
    }

    @Override
    public boolean support(TextMessage weChatMessage) {
        return true;
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.text;
    }
}
