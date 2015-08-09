package org.dptech.wx.sdk.message;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dptech.wx.sdk.common.Interfaces;
import org.dptech.wx.sdk.exception.ErrorCheckUtil;
import org.dptech.wx.sdk.util.HttpUtil;

public class MessageAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageAPI.class);
	
	private MessageAPI(){}
	
	private static final MessageAPI api = new MessageAPI();
	
	public static MessageAPI getInstance(){
		return api;
	}
	
	/**
	 * 发送客服消息  -  文本消息
	 */
	public void sendCustomTextMessage(String accessToken, String toUserOpenId, String content){
		String msg = MessageFactory.customTextMessage(toUserOpenId, content);
		String url = Interfaces.custom_send.toString() + "?access_token="+accessToken;
		try {
			String json = HttpUtil.postJson(url, msg);
			ErrorCheckUtil.check(json);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}
	
}