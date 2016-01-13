package org.singledog.wechat.sdk.message.reply;

import org.singledog.wechat.sdk.message.AbstractMessage;
import org.singledog.wechat.sdk.message.WeChatMessage;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
public class ReplyStringMessage extends ReplyTextMessage {

    private String rawMessage;

    public ReplyStringMessage() {
    }

    public ReplyStringMessage(WeChatMessage weChatMessage, String rawMessage) {
        super(weChatMessage);
        this.rawMessage = rawMessage;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    @Override
    public String toXml() {
        return rawMessage;
    }
}
