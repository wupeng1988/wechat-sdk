package org.dptech.wx.sdk.message;

import org.dptech.wx.sdk.model.push.TextMessage;

public interface TextMessageHandler {
	
	public boolean support(TextMessage textMessage);
	
	public String handle(TextMessage textMessage);
	
}