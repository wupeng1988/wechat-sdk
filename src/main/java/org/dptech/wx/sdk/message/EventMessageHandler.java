package org.dptech.wx.sdk.message;

import org.dptech.wx.sdk.common.WechatPushedEvent;

public interface EventMessageHandler {
	
	public boolean support(WechatPushedEvent event);
	
	public String handle(WechatPushedEvent event);
	
}
