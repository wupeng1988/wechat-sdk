package org.singledog.wechat.sdk.message.reply;

import org.singledog.wechat.sdk.message.AbstractMessage;
import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.WeChatMessage;

/**
 * Created by adam on 16-1-4.
 */
public class ReplyTextMessage extends AbstractMessage {

    private String Content;

    public ReplyTextMessage(){}

    public ReplyTextMessage(WeChatMessage message){
        this.setFromUserName(message.getToUserName());
        this.setToUserName(message.getFromUserName());
        this.setMsgType(MessageTypes.text.name());
        this.setCreateTime(System.currentTimeMillis() / 1000);
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
