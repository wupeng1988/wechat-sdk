package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.handler.analyzer.MessageAnalyzer;
import org.singledog.wechat.sdk.handler.analyzer.MessageAnalyzerHolder;
import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
public abstract class RobotProxyHandler<T extends WeChatMessage> implements MessageHandler<T> {
    private static final Logger logger = LoggerFactory.getLogger(RobotProxyHandler.class);

    @Autowired
    private MessageAnalyzerHolder analyzerHolder;

    public WeChatMessage handle(T message) {
        List<MessageAnalyzer<? extends WeChatMessage>> analyzers = analyzerHolder.getAnalyzers(type().name());
        for (MessageAnalyzer analyzer : analyzers) {
            if (analyzer.support(message)) {
                logger.debug("found supoorted analyzer : {}", analyzer);
                return analyzer.analyze(message);
            }
        }

        return null;
    }

    public abstract MessageTypes type();
}
