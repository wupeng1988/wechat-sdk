package org.singledog.wechat.sdk.handler.analyzer.robot;

import org.singledog.wechat.sdk.message.ImageMessage;
import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.message.reply.ReplyTextMessage;
import org.singledog.wechat.sdk.util.HttpUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
@Component
@Profile("enable-robot")
public class RobotImageAnalyzer extends RobotProxyAnalyzer<ImageMessage> {
    @Override
    public MessageTypes type() {
        return MessageTypes.image;
    }
}
