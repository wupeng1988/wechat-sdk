package org.dptech.wx.sdk.message;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dptech.wx.sdk.cfg.AutoReplyMessageConfig;
import org.dptech.wx.sdk.cfg.InstanceFactory;
import org.dptech.wx.sdk.model.config.AutoReplyMessage;
import org.dptech.wx.sdk.model.push.TextMessage;
import org.dptech.wx.sdk.util.BeanUtil;
import org.dptech.wx.sdk.util.WxUtil;
import org.dptech.wx.sdk.util.XmlUtil2;

/**
 * 
 * 回复信息
 * 
 * @author wupeng
 *
 */
public class TextMessageDispatcher implements MessageTypeHandler{

	private static final Logger logger = LoggerFactory.getLogger(TextMessageDispatcher.class);
	
	@Override
	public boolean support(Map<String, String> xmlMap) {
		if("text".equals(xmlMap.get(MESSAGE_TYPE))){
			return true;
		}
		
		return false;
	}

	@Override
	public String handle(String xml, Map<String, String> xmlMap) throws Exception{
		TextMessage textMessage =  BeanUtil.mapToBean(XmlUtil2.toMap(xml), TextMessage.class);
		if(textMessage == null){
			logger.error(new StringBuffer("user send message [ ").append(xml).append(" ]\n, but convert xml to bean failed !!").toString());
			return "";
		}
		
		AutoReplyMessage message = AutoReplyMessageConfig.getReplyMessage(textMessage.getMsgType(), textMessage.getContent());
		if(message == null){
			message = AutoReplyMessageConfig.getReplyMessage(textMessage.getMsgType(), "default");
		}
		if(message == null){
			logger.error(new StringBuffer("user send message [ ").append(xml).append(" ]\n, but no auto reply message configed !").toString());
			return "";
		}
		
		String response = "";
		switch(message.getHandlerType()){
		case AutoReplyMessageConfig.MESSAGE_RESPONSE_TYPE_TEXT : 
			response = WxUtil.replaceVariables(message.getValue(), xmlMap);
			break;
			
		case AutoReplyMessageConfig.MESSAGE_RESPONSE_TYPE_CLASS : {
			TextMessageHandler handler = this.getTextHandlerByClass(message.getValue());
			if(handler != null){
				response = handler.handle(textMessage);
			}
			break;
		}
		case AutoReplyMessageConfig.MESSAGE_RESPONSE_TYPE_BEAN : {
			TextMessageHandler handler = this.getTextHandlerFromSpring(message.getValue());
			if(handler != null){
				response = handler.handle(textMessage);
			}
			break;
		}
		}
		
		if("".equals(response)){
			logger.error(new StringBuffer("no response returned for ").
					append(message.getHandlerType()).append(message.getValue()).toString());
		}
		
		return response;
	}
	
	private TextMessageHandler getTextHandlerByClass(String className){
		
		TextMessageHandler handler = null;
		try {
			handler = (TextMessageHandler) InstanceFactory.getInstanceByClass(Class.forName(className));
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		
		return handler;
	}
	
	private TextMessageHandler getTextHandlerFromSpring(String beanName){
		return InstanceFactory.getInstanceFromSpring(beanName, TextMessageHandler.class);
	}

	@Override
	public String messageType() {
		return TYPE_TEXT;
	}
	
}