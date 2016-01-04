package org.singledog.wechat.sdk.message.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.util.DateUtil;
import org.singledog.wechat.sdk.util.JsonUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by adam on 1/4/16.
 */
@Document(collection = "wechat_messages")
public class MessageEntity {
    @Id
    private String id;
    private String toUserName;
    private String fromUserName;
    private String msgType;
    private String json;
    private String createTime;
    private String replyId;


    public MessageEntity() {
        this.setCreateTime(DateUtil.formatTime(new Date()));
    }

    public MessageEntity(WeChatMessage weChatMessage) {
        this.setCreateTime(DateUtil.formatTime(new Date()));
        this.setToUserName(weChatMessage.getToUserName());
        this.setFromUserName(weChatMessage.getFromUserName());
        this.setMsgType(weChatMessage.getMsgType());
        try {
            this.setJson(JsonUtil.toJson(weChatMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
}
