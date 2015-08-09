package org.dptech.wx.sdk.cfg;

import java.io.File;
import java.util.HashMap;
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
import org.dptech.wx.sdk.model.config.AutoReplyMessage;
/**
 * 
 * 自动回复的测试
 * 
 * @author wupeng
 *
 */
public class AutoReplyMessageConfig {
	
	public static final String MESSAGE_RESPONSE_TYPE_TEXT = "text";
	public static final String MESSAGE_RESPONSE_TYPE_CLASS = "class";
	public static final String MESSAGE_RESPONSE_TYPE_BEAN = "bean";
	
	private static final Logger logger = LoggerFactory.getLogger(AutoReplyMessageConfig.class);
	
	// msg_type -> (keyword -> object)
	private static final Map<String, Map<String, AutoReplyMessage>> config = new ConcurrentHashMap<>();
	private static final ReadWriteLock lock = new ReentrantReadWriteLock(); 
	
	static {
		try {
			reload();
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	private static void reload() throws DocumentException {
		String configDir = WeChatConfig.getValue(WeChatConfig.k_config_dir) + File.separator + "auto_reply_cfg.xml";
		SAXReader reader = new SAXReader();
		File file = new File(configDir);
		Document document = reader.read(file);
		Element root = document.getRootElement();
		
		lock.writeLock().lock();
		try {
			config.clear();
			parse(root);
		} finally {
			lock.writeLock().unlock();
		}
		
		file = null;
		reader = null;
		document = null;
		root = null;
	}
	
	@SuppressWarnings("unchecked")
	private static void parse(Element rootElement){
		List<Element> groups = rootElement.elements("message-group");
		for(Element group : groups){
			String msgType = group.attributeValue("type");
			Map<String, AutoReplyMessage> messageMap = new HashMap<String, AutoReplyMessage>();
			
			List<Element> messages = group.elements("message");
			for(Element message : messages){
				String keyWord = message.attributeValue("keyword");
				String handlerType = message.attributeValue("handler_type");
				if(handlerType == null || "".equals(handlerType)){
					handlerType = MESSAGE_RESPONSE_TYPE_TEXT;
				}
				
				AutoReplyMessage t = new AutoReplyMessage();
				if(MESSAGE_RESPONSE_TYPE_TEXT.equals(handlerType)){
					t.setValue(message.getTextTrim());
				} else {
					t.setValue(message.attributeValue("value"));
				}
				
				t.setHandlerType(handlerType);
				t.setKeyWord(keyWord);
				t.setMessageType(msgType);
				messageMap.put(keyWord, t);
			}
			
			config.put(msgType, messageMap);
		}
	}
	
	public static AutoReplyMessage getReplyMessage(String messageType, String keyword){
		lock.readLock().lock();
		try {
			Map<String, AutoReplyMessage> map = config.get(messageType);
			if(map != null){
				return map.get(keyword);
			}
		} finally {
			lock.readLock().unlock();
		}
		
		return null;
	}
	
}