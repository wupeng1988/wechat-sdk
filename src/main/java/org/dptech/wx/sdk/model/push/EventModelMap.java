package org.dptech.wx.sdk.model.push;

import java.util.HashMap;
import java.util.Map;

import org.dptech.wx.sdk.common.WechatPushedEvent;

public class EventModelMap {
	
	private final Map<String, Class<? extends WechatPushedEvent>> event_mapping = new HashMap<>(); 
	
	public void put(String key, Class<? extends WechatPushedEvent> value){
		this.event_mapping.put(key, value);
	}
	
	public Class<? extends WechatPushedEvent> get(String key) {
		return this.event_mapping.get(key);
	}
	
}
