package org.dptech.wx.sdk.message;

import java.util.Map;

/**
 * 
 * 关注事件，未关注时候扫描二维码事件
 * 
 * @author wupeng
 *
 */
public class SubscribeEventHandler implements MessageTypeHandler{

	@Override
	public String handle(String xml, Map<String, String> xmlMap) throws Exception {
		
		return null;
	}

	@Override
	public String messageType() {
		return TYPE_EVENT;
	}

}