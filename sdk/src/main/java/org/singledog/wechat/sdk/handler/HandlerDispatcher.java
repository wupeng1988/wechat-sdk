package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by adam on 1/3/16.
 */
@Component
public class HandlerDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(HandlerDispatcher .class);

    private final Map<Class<?>, MessageHandler<? extends WeChatMessage>> messageHandlerMap;

    @Autowired(required = false)
    public HandlerDispatcher(List<MessageHandler<? extends WeChatMessage>> messageHandlers) {
       Map<Class<?>, MessageHandler<?>> map = new HashMap<>();
        for (MessageHandler handler : messageHandlers) {
            logger.debug("loading handler : {}", handler);
            map.put(handler.support(), handler);
        }

        this.messageHandlerMap = Collections.unmodifiableMap(map);
    }


    public MessageHandler<? extends WeChatMessage> getMessageHandler(Class<?> clazz) {
        return this.messageHandlerMap.get(clazz);
    }

}
