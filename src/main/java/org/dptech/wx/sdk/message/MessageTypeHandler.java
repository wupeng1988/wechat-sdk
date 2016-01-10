package org.dptech.wx.sdk.message;

import java.util.Map;

/**
 * 
 * 处理微信过来的消息
 * 
 * @author wupeng
 *
 */
public interface MessageTypeHandler {
	
	String MESSAGE_TYPE = "MsgType";
	String EVENT = "Event";
	String TO_USERNAME = "ToUserName";
	String FROM_USERNAME = "FromUserName";
	String CREATE_TIME = "CreateTime";
	
	/**
	 * message type -  text
	 */
	String TYPE_TEXT = "text";
	/**
	 * message type event
	 */
	String TYPE_EVENT = "event";
	
	public String messageType();
	
	public String handle(String xml, Map<String, String> xmlMap) throws Exception;

}