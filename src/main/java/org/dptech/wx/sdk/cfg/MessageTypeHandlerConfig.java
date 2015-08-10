package org.dptech.wx.sdk.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dptech.wx.sdk.message.MessageTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageTypeHandlerConfig {
	private static final Logger logger = LoggerFactory.getLogger(MessageTypeHandlerConfig.class);
	
	private static final Map<String, MessageTypeHandler> handler_cache = new HashMap<>();
	
	@Autowired
	public MessageTypeHandlerConfig(List<MessageTypeHandler> messageTypeHandlers){
		for(MessageTypeHandler handler : messageTypeHandlers) {
			logger.error("register messageType handler : {} for message Type : {}", handler.getClass().getName(), handler.messageType());
			handler_cache.put(handler.messageType(), handler);
		}
	}
	
	public MessageTypeHandler getHandler(String messageType){
		return handler_cache.get(messageType);
	}
	
}