package org.dptech.wx.sdk.common;

/**
 * 微信推送的普通消息
 * 
 * @author wupeng
 *
 */
public interface GeneralMessage extends WechatPushedMessage{
	
	public String getMsgId();

}