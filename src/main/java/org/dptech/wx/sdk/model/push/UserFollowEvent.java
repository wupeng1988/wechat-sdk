package org.dptech.wx.sdk.model.push;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.dptech.wx.sdk.common.WechatPushedEvent;
/**
 * 
 * 用户关注/取消关注的事件bean
 * 
 * @author wupeng
 *
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserFollowEvent implements WechatPushedEvent{

	public static final String event_subscribe = "subscribe";
	public static final String event_unsubscribe = "unsubscribe";

	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	private String Event;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

}