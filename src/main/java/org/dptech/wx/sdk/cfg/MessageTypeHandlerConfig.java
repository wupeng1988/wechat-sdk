package org.dptech.wx.sdk.cfg;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dptech.wx.sdk.WeChatConfig;
import org.dptech.wx.sdk.message.MessageTypeHandler;

public class MessageTypeHandlerConfig {
	
	public static final String HANDLER_TYPE_CLASS = "class";
	public static final String HANDLER_TYPE_BEAN = "bean";
	
	private static final Logger logger = LoggerFactory.getLogger(MessageTypeHandlerConfig.class);
	private static final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private static final Map<String, List<MessageTypeHandler>> handler_cache = new ConcurrentHashMap<>();
	
	static {
		try {
			reload();
		} catch (ClassNotFoundException | DocumentException e) {
			logger.error(e.getMessage(), e);
			System.exit(1);
		}
	}
	
	public static void reload() throws DocumentException, ClassNotFoundException{
		String config = WeChatConfig.getValue(WeChatConfig.k_config_dir) + File.separator + "auto_reply_cfg.xml";
		File file = new File(config);
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		Element root = document.getRootElement();
		
		lock.writeLock().lock();
		try {
			@SuppressWarnings("unchecked")
			List<Element> messageTypes = root.elements("msg-type");
			handler_cache.clear();
			for(Element msgTypeElement : messageTypes){
				String msgType = msgTypeElement.attributeValue("name");
				@SuppressWarnings("unchecked")
				List<Element> handlers = msgTypeElement.elements("handler");
				List<MessageTypeHandler> models = new ArrayList<>(); 
				for(Element handlerElement : handlers){
					String type = handlerElement.attributeValue("type");
					String value = handlerElement.attributeValue("name");
					MessageTypeHandler mh = getHandlers(type, value);
					if(mh != null)
						models.add(mh);
					logger.info("regist message type handler : " + type + " , " +  value);
				}
				
				handler_cache.put(msgType, models);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	private static MessageTypeHandler getHandlers(String type, String value) throws ClassNotFoundException{
		MessageTypeHandler handler = null;
		
		if(HANDLER_TYPE_CLASS.equals(type)){
			handler = (MessageTypeHandler) InstanceFactory.getInstanceByClass(Class.forName(value));
		} else if(HANDLER_TYPE_BEAN.equals(type)) {
			handler = InstanceFactory.getInstanceFromSpring(value, MessageTypeHandler.class);
		}
		
		return handler;
	}
	
	@SuppressWarnings("unchecked")
	public static List<MessageTypeHandler> getHandlers(String messageType){
		List<MessageTypeHandler> handlers = null;
		
		lock.readLock().lock();
		try {
			 handlers = handler_cache.get(messageType);
		} finally {
			lock.readLock().unlock();
		}
		
		if(handlers == null){
			handlers = Collections.EMPTY_LIST;
		}
		return handlers;
	}
	
}