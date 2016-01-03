package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 15-12-31.
 */
public class VoiceMessage extends AbstractMessage {

    private String MediaId;
    private String Format;
    private String MsgId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
