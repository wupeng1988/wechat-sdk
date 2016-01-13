package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.TextMessage;
import org.springframework.stereotype.Component;

/**
 * Created by adam on 1/3/16.
 */
@Component
public class TextMessageHandler extends RobotProxyHandler<TextMessage> {

    @Override
    public Class<TextMessage> support() {
        return TextMessage.class;
    }

    @Override
    public MessageTypes type() {
        return MessageTypes.text;
    }
}
