package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 15-12-31.
 */
public interface MessageHandler<T extends WeChatMessage> {

    public Class<T> support();

    public WeChatMessage handle(T message);

}
