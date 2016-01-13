package org.singledog.wechat.sdk.handler.analyzer.robot;

import org.singledog.wechat.sdk.handler.analyzer.MessageAnalyzer;
import org.singledog.wechat.sdk.holder.ThreadLocalHolder;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.message.reply.ReplyStringMessage;
import org.singledog.wechat.sdk.message.reply.ReplyTextMessage;
import org.singledog.wechat.sdk.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
public abstract class RobotProxyAnalyzer<T extends WeChatMessage> implements MessageAnalyzer<T> {
    private static final Logger logger = LoggerFactory.getLogger(RobotProxyAnalyzer.class);

    @Value("${wechat.sdk.robot.accessUrl}")
    protected String accessUrl;

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public WeChatMessage analyze(T weChatMessage) {
        String originalXml = ThreadLocalHolder.getOriginalXml();
        logger.debug("received message : {}", originalXml);

        String response = null;
        try {
            response = HttpUtil.post(accessUrl, originalXml);
        } catch (Exception e) {
            response = "Oops... 出错了...";
            ReplyTextMessage textMessage = new ReplyTextMessage(weChatMessage);
            textMessage.setContent(response);
            logger.error(e.getMessage(), e);
            return textMessage;
        }

        logger.debug("receive message from robot : {}", response);

        return new ReplyStringMessage(weChatMessage, response);
    }

    @Override
    public boolean support(T weChatMessage) {
        return true;
    }

}
