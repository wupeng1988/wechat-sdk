package org.dptech.wx.sdk.common;

/**
 * 微信推送的事件
 * 
 * @author wupeng
 *
 */
public interface WechatPushedEvent extends WechatPushedMessage{
	
	public String getEvent();

}