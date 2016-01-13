package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.message.ImageMessage;
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
public class ImageMessageHandler extends RobotProxyHandler<ImageMessage> {
    @Override
    public MessageTypes type() {
        return MessageTypes.image;
    }

    @Override
    public Class<ImageMessage> support() {
        return ImageMessage.class;
    }
}
