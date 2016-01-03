package org.singledog.wechat.sdk.message;

/**
 *
 * basic message information
 *
 * Created by adam on 15-12-31.
 */
public abstract class AbstractMessage implements WeChatMessage {

    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String MsgType;

    @Override
    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    @Override
    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    @Override
    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    @Override
    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
