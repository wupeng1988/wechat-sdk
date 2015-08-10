package org.dptech.wx.sdk.message;

import java.util.List;
import java.util.Map;

import org.dptech.wx.sdk.cfg.AutoReplyMessageConfig;
import org.dptech.wx.sdk.common.WechatPushedEvent;
import org.dptech.wx.sdk.model.config.AutoReplyMessage;
import org.dptech.wx.sdk.model.push.EventModelMap;
import org.dptech.wx.sdk.util.BeanUtil;
import org.dptech.wx.sdk.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 
 * 推送事件总调度
 * 
 * @author wupeng
 *
 */
@Component
public class EventMessageDispatcher implements MessageTypeHandler{

	private static final Logger logger = LoggerFactory.getLogger(EventMessageDispatcher.class);
	
	@Autowired
	private EventModelMap eventModelMap;
	
	@Autowired
	private List<EventMessageHandler> messageHandlers;
	
	@Override
	public String handle(String xml, Map<String, String> xmlMap) throws Exception {
		String msgType = xmlMap.get("MsgType");
		String event = xmlMap.get("Event");
		
		WechatPushedEvent pushedEvent = BeanUtil.mapToBean(xmlMap, eventModelMap.get(event));
		
		EventMessageHandler handler = null;
		for(EventMessageHandler handlerTemp : messageHandlers) {//handler优先
			if(handlerTemp.support(pushedEvent)) {
				handler = handlerTemp;
				break;
			}
		}
		
		String response = "";
		if(handler != null) {//如果有handler， 则执行handler，不执行自动回复
			response = handler.handle(pushedEvent);
			response = WxUtil.replaceVariables(response, xmlMap);
		} else {//如果没有handler, 则执行自动回复
			AutoReplyMessage message = AutoReplyMessageConfig.getReplyMessage(msgType, event);
			if(message == null){
				logger.error(new StringBuffer("user send message [ ").append(xml).append(" ]\n, but no auto reply message configed !").toString());
			} else {
				response = WxUtil.replaceVariables(message.getValue(), xmlMap);
			}
			
			if("".equals(response)){
				logger.error(new StringBuffer("no response returned for ").
						append(message.getHandlerType()).append(message.getValue()).toString());
			}
		}
		
		
		return response;
	}
	
	@Override
	public String messageType() {
		return TYPE_EVENT;
	} 

}