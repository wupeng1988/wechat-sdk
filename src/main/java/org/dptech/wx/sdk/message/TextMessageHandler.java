package org.dptech.wx.sdk.message;

import org.dptech.wx.sdk.model.push.TextMessage;

public interface TextMessageHandler {
	
	public String handle(TextMessage textMessage);
	
}