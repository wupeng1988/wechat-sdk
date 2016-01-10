package org.dptech.wx.sdk.message;

import java.util.HashMap;
import java.util.Map;

import org.dptech.wx.sdk.model.PassiveTextMessage;
import org.dptech.wx.sdk.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MessageFactory {
	/**
	 * 被动回复消息 - 简单文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static PassiveTextMessage simpleMessage(String toUserName, String fromUserName, String content){
		PassiveTextMessage message = new PassiveTextMessage();
		message.setContent(content);
		message.setCreateTime(System.currentTimeMillis());
		message.setFromUserName(fromUserName);
		message.setToUserName(toUserName);
		return message;
	}
	
	/**
	 * 客服消息
	 * 
	 * 文本消息
	 * 
	 * @return
	 */
	public static String customTextMessage(String toUserName, String content){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", toUserName);
		map.put("msgtype", "text");
		
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("content", content);
		map.put("text", contentMap);
		try {
			return JsonUtil.toJson(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(customTextMessage("aaaaaaaa", "asdfhkhkjhkjasdgfdsGasdfsgfdsg"));
	}
	
}