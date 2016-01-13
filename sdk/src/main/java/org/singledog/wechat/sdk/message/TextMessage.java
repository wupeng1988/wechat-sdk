package org.singledog.wechat.sdk.message;

/**
 * text message
 * <p/>
 * Created by adam on 15-12-31.
 */
public class TextMessage extends AbstractMessage {

    private String Content;

    private String MsgId;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
