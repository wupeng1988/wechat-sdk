package org.dptech.wx.sdk.message;

import java.util.List;
import java.util.Map;

import org.dptech.wx.sdk.cfg.AutoReplyMessageConfig;
import org.dptech.wx.sdk.model.config.AutoReplyMessage;
import org.dptech.wx.sdk.model.push.TextMessage;
import org.dptech.wx.sdk.util.BeanUtil;
import org.dptech.wx.sdk.util.WxUtil;
import org.dptech.wx.sdk.util.XmlUtil2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 回复信息
 * 
 * @author wupeng
 *
 */
@Component
public class TextMessageDispatcher implements MessageTypeHandler{

	private static final Logger logger = LoggerFactory.getLogger(TextMessageDispatcher.class);
	
	@Autowired
	private List<TextMessageHandler> messageHandlers;
	
	@Override
	public String handle(String xml, Map<String, String> xmlMap) throws Exception{
		TextMessage textMessage =  BeanUtil.mapToBean(XmlUtil2.toMap(xml), TextMessage.class);
		if(textMessage == null){
			logger.error(new StringBuffer("user send message [ ").append(xml).append(" ]\n, but convert xml to bean failed !!").toString());
			return "";
		}
		
		TextMessageHandler handler = null;
		for(TextMessageHandler handlerTemp : messageHandlers) {
			if(handlerTemp.support(textMessage)) {
				handler = handlerTemp;
				break;
			}
		}
		
		if(handler != null) {
			return WxUtil.replaceVariables(handler.handle(textMessage), xmlMap);
		}

		String response = "";
		AutoReplyMessage message = AutoReplyMessageConfig.getReplyMessage(textMessage.getMsgType(), textMessage.getContent());
		if(message == null){
			message = AutoReplyMessageConfig.getReplyMessage(textMessage.getMsgType(), "default");
			response = WxUtil.replaceVariables(response, xmlMap);
		}
		
		if(message == null){
			logger.error(new StringBuffer("user send message [ ").append(xml).append(" ]\n, but no auto reply message configed !").toString());
		}
		
		
		if("".equals(response)){
			logger.error(new StringBuffer("no response returned for ").
					append(message.getHandlerType()).append(message.getValue()).toString());
		}
		
		return response;
	}
	
	@Override
	public String messageType() {
		return TYPE_TEXT;
	}
	
}