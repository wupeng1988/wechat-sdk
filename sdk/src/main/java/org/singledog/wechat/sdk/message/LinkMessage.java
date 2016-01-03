package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 15-12-31.
 */
public class LinkMessage extends AbstractMessage {

    private String Title;
    private String Description;
    private String Url;
    private String MsgId;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
