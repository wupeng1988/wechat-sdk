package org.singledog.wechat.sdk.handler.analyzer;

import org.singledog.wechat.sdk.message.WeChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 * Created by adam on 1/4/16.
 */
@Component
public class MessageAnalyzerHolder {

    private final Map<String, List<MessageAnalyzer<? extends WeChatMessage>>> analyzerMap;

    @Autowired(required = false)
    public MessageAnalyzerHolder(List<MessageAnalyzer<? extends WeChatMessage>> analyzerList) {
        Map<String, List<MessageAnalyzer<? extends WeChatMessage>>> map = new HashMap<>();

        for (MessageAnalyzer<? extends WeChatMessage> analyzer : analyzerList) {
            String type = analyzer.type().name();
            List<MessageAnalyzer<? extends WeChatMessage>> list = map.get(type);
            if (list == null) {
                list = new LinkedList<>();
                map.put(type, list);
            }

            list.add(analyzer);
        }

        for (Map.Entry<String, List<MessageAnalyzer<? extends WeChatMessage>>> entry : map.entrySet()) {
            Collections.sort(entry.getValue(), new AnalyzerComparator());
        }

        analyzerMap = Collections.unmodifiableMap(map);
    }


    public List<MessageAnalyzer<? extends WeChatMessage>> getAnalyzers(String type) {
        return analyzerMap.get(type);
    }


    private static class AnalyzerComparator implements Comparator<MessageAnalyzer<?>> {
        @Override
        public int compare(MessageAnalyzer<?> o1, MessageAnalyzer<?> o2) {
            return o1.order() - o2.order();
        }
    }


}
