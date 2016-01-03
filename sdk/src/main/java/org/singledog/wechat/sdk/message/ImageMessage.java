package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 15-12-31.
 */
public class ImageMessage extends AbstractMessage {

    private String PicUrl;
    private String MediaId;
    private String MsgId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
