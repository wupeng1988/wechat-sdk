package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.TextMessage;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * Created by adam on 1/3/16.
 */
@Component
public class TextMessageHandler implements MessageHandler<TextMessage> {
    private static final Logger logger = LoggerFactory.getLogger(TextMessage.class);

    @Override
    public Class<TextMessage> support() {
        return TextMessage.class;
    }

    @Override
    public WeChatMessage handle(TextMessage message) {
        //TODO
        return null;
    }
}
