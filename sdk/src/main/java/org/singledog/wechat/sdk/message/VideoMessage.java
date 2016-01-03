package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 15-12-31.
 */
public class VideoMessage extends AbstractMessage {

    private String MediaId;
    private String ThumbMediaId;
    private String MsgId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
