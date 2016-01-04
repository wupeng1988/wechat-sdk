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

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>\n")
                .append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]</ToUserName>\n")
                .append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]</FromUserName>\n")
                .append("<CreateTime>").append(this.getCreateTime()).append("</CreateTime>\n")
                .append("<MsgType><![CDATA[text]]></MsgType>\n")
                .append("<Content><![CDATA[").append(this.getContent()).append("]]></Content>\n")
                .append("</xml>");
        return sb.toString();
    }
}
