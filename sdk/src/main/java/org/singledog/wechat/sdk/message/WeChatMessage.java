package org.singledog.wechat.sdk.message;

/**
 *
 * Message
 *
 * Created by adam on 15-12-31.
 */
public interface WeChatMessage {

    String getToUserName();

    String getFromUserName();

    long getCreateTime();

    String getMsgType();
}
