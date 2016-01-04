package org.singledog.wechat.sdk.handler.analyzer.robot;

import org.singledog.wechat.sdk.handler.analyzer.MessageAnalyzer;
import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.TextMessage;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * this will be called last
 *
 * Created by adam on 16-1-4.
 */
@Component
@Profile("enable-robot")
public class RobotTextAnalyzer implements MessageAnalyzer<TextMessage> {

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
        return null;
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
