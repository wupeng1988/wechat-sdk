package org.dptech.wx.sdk.model;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.dptech.wx.sdk.util.XmlUtil;
/**
 * 被动回复消息
 * 
 * 简单文本消息
 * @author wupeng
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class PassiveTextMessage {

	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType = "text";
	private String Content;

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

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	public String toXml() throws JAXBException{
		String xml = XmlUtil.convertToXml(this); 
		xml = XmlUtil.removeDeclare(xml);
		return xml;
	}

}