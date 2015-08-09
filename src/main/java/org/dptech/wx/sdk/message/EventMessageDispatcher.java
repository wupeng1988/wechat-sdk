package org.dptech.wx.sdk.message;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dptech.wx.sdk.cfg.AutoReplyMessageConfig;
import org.dptech.wx.sdk.cfg.InstanceFactory;
import org.dptech.wx.sdk.model.config.AutoReplyMessage;
import org.dptech.wx.sdk.util.WxUtil;
/**
 * 
 * 推送事件总调度
 * 
 * @author wupeng
 *
 */
public class EventMessageDispatcher implements MessageTypeHandler{

	private static final Logger logger = LoggerFactory.getLogger(EventMessageDispatcher.class);
	
	@Override
	public boolean support(Map<String, String> xmlMap) {
		if("event".equals(xmlMap.get(MESSAGE_TYPE)))
			return true;
		
		return false;
	}

	@Override
	public String handle(String xml, Map<String, String> xmlMap) throws Exception {
		String msgType = xmlMap.get("MsgType");
		String event = xmlMap.get("Event");
		
		AutoReplyMessage message = AutoReplyMessageConfig.getReplyMessage(msgType, event);
		if(message == null){
			logger.error(new StringBuffer("user send message [ ").append(xml).append(" ]\n, but no auto reply message configed !").toString());
			return "";
		}
		
		String response = "";
		switch(message.getHandlerType()){
		case AutoReplyMessageConfig.MESSAGE_RESPONSE_TYPE_TEXT : {
			response = message.getValue();
			WxUtil.replaceVariables(response, xmlMap);
			break;
		}
		case AutoReplyMessageConfig.MESSAGE_RESPONSE_TYPE_CLASS : {
			MessageTypeHandler handler = this.getHandlerByClass(message.getValue()); 
			if(handler != null && handler.support(xmlMap)){
				response = handler.handle(xml, xmlMap);
			}
			break;
		}
		case AutoReplyMessageConfig.MESSAGE_RESPONSE_TYPE_BEAN : { 
			MessageTypeHandler handler = InstanceFactory.getInstanceFromSpring(message.getValue(), MessageTypeHandler.class);
			if(handler != null && handler.support(xmlMap)){
				response = handler.handle(xml, xmlMap);
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
	
	private MessageTypeHandler getHandlerByClass(String className){
		try {
			return (MessageTypeHandler) InstanceFactory.getInstanceByClass(Class.forName(className));
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}

	@Override
	public String messageType() {
		return TYPE_EVENT;
	} 

}