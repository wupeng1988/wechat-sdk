package org.singledog.wechat.sdk.message;

import org.singledog.wechat.sdk.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by adam on 1/3/16.
 */
@Component
public class MessageFactory {

    private static final Logger logger = LoggerFactory.getLogger(MessageFactory.class);

    public WeChatMessage getMessage(Map<String, String> messageMap) {
        Class<? extends WeChatMessage> clazz = this.getMessageClass(messageMap);
        if (clazz != null) {
            return BeanUtil.mapToBean(messageMap, clazz);
        }

        return null;
    }


    public Class<? extends WeChatMessage> getMessageClass(Map<String, String> messageMap) {
        String messageType = messageMap.get("MsgType");
        if (StringUtils.isEmpty(messageType)) {
            logger.error("can not found message type ! ");
            return null;
        }

        if (MessageTypes.event.name().equals(messageType)) {
            String event = messageMap.get("Event");
            if (StringUtils.isEmpty(event)) {
                logger.error("can not found event for event message !");
                return null;
            }

            EventTypes eventTypes = EventTypes.valueOf(event);
            return eventTypes != null ? eventTypes.getMappingClass() : null;
        } else {
            MessageTypes messageTypes = MessageTypes.valueOf(messageType);
            return messageTypes != null ? messageTypes.getMappingClass() : null;
        }
    }
}
