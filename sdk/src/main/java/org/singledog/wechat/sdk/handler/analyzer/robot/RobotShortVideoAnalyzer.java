package org.singledog.wechat.sdk.handler.analyzer.robot;

import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.ShortVideoMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
@Component
@Profile("enable-robot")
public class RobotShortVideoAnalyzer extends RobotProxyAnalyzer<ShortVideoMessage> {
    @Override
    public MessageTypes type() {
        return MessageTypes.shortvideo;
    }
}