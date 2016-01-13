package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.handler.analyzer.MessageAnalyzer;
import org.singledog.wechat.sdk.handler.analyzer.MessageAnalyzerHolder;
import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.MessageTypes;
import org.singledog.wechat.sdk.message.TextMessage;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by adam on 1/3/16.
 */
@Component
public class TextMessageHandler implements MessageHandler<TextMessage> {
    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    @Autowired
    private MessageAnalyzerHolder analyzerHolder;

    @Override
    public Class<TextMessage> support() {
        return TextMessage.class;
    }

    @Override
    public WeChatMessage handle(TextMessage message) {
        List<MessageAnalyzer<? extends WeChatMessage>> analyzers = analyzerHolder.getAnalyzers(MessageTypes.text.name());
        for (MessageAnalyzer analyzer : analyzers) {
            if (analyzer.support(message)) {
                logger.debug("found supoorted analyzer : {}", analyzer);
                return analyzer.analyze(message);
            }
        }

        return null;
    }
}
