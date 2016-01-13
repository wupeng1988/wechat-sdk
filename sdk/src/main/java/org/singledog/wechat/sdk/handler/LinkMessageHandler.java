package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.message.LinkMessage;
import org.singledog.wechat.sdk.message.MessageTypes;
import org.springframework.stereotype.Component;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class LinkMessageHandler extends RobotProxyHandler<LinkMessage> {
    @Override
    public MessageTypes type() {
        return MessageTypes.voice;
    }

    @Override
    public Class<LinkMessage> support() {
        return LinkMessage.class;
    }
}
