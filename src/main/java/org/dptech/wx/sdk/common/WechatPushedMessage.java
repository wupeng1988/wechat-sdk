package org.dptech.wx.sdk.common;

/**
 * 是微信推送的消息
 * @author wupeng
 *
 */
public interface WechatPushedMessage {

	public String getMsgType();
	
	public String getToUserName();
	
	public String getFromUserName();
	
	public long getCreateTime();
	
}