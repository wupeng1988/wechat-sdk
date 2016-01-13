package org.singledog.wechat.sdk.message.reply;

import org.singledog.wechat.sdk.message.AbstractMessage;
import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.WeChatMessage;

/**
 * Created by adam on 16-1-4.
 */
public class ReplyTextMessage extends AbstractMessage {

    private String Content;

    public ReplyTextMessage() {
    }

    public ReplyTextMessage(WeChatMessage message) {
        this.setFromUserName(message.getToUserName());
        this.setToUserName(message.getFromUserName());
        this.setMsgType(MessageTypes.text.name());
        this.setCreateTime(System.currentTimeMillis());
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>")
                .append("<ToUserName>")
                .append("<![CDATA[").append(this.getToUserName()).append("]]>")
                .append("</ToUserName>")
                .append("<FromUserName>")
                .append("<![CDATA[").append(this.getFromUserName()).append("]]>")
                .append("</FromUserName>")
                .append("<CreateTime>").append(this.getCreateTime()).append("</CreateTime>")
                .append("<MsgType>")
                .append("<![CDATA[text]]>")
                .append("</MsgType>")
                .append("<Content>")
                .append("<![CDATA[").append(this.getContent()).append("]]>")
                .append("</Content>")
                .append("</xml>");
        return sb.toString();
    }
}
