package org.dptech.wx.sdk.cfg;

import org.dptech.wx.sdk.model.push.EventModelMap;
import org.dptech.wx.sdk.model.push.ScanQRCodeEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventModelConfig {

	@Bean
	public EventModelMap eventModelMap(){
		EventModelMap map = new EventModelMap();
		map.put("subscribe", ScanQRCodeEvent.class);
		map.put("unsubscribe", ScanQRCodeEvent.class);
		return map;
	}
	
}
