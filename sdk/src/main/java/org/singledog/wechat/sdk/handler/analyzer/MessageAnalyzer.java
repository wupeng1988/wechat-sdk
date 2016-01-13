package org.singledog.wechat.sdk.handler.analyzer;

import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.WeChatMessage;

/**
 * Created by adam on 1/4/16.
 */
public interface MessageAnalyzer<T extends WeChatMessage> {

    /**
     * the order to be executed, the smaller will be executed first.
     *
     * @return
     */
    public int order();

    /**
     * response
     *
     * @param weChatMessage
     * @return
     */
    public WeChatMessage analyze(T weChatMessage);

    /**
     * @param weChatMessage
     * @return
     */
    public boolean support(T weChatMessage);

    /**
     * analyzer only for this type of message
     *
     * @return
     */
    public MessageTypes type();

}
