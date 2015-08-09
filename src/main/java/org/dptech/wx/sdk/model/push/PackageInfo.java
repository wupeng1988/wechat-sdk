package org.dptech.wx.sdk.model.push;

import org.dptech.wx.sdk.util.XmlUtil;

/**
 * 
 * 这个包里面的类，对应微信推送过来的消息
 * 
 * @author wupeng
 *
 */
public class PackageInfo {
	
	
	public static void main(String[] args) throws Exception {
		
		String xml = " <xml> "
					+" <ToUserName>toUser</ToUserName> "
					+" <FromUserName>fromUser</FromUserName>  "
					+" <CreateTime>1348831860</CreateTime> "
					+" <MsgType>text</MsgType> "
					+" <Content>this is a test</Content> "
					+" <MsgId>1234567890123456</MsgId> "
					+" </xml> ";
		
		
		TextMessage message = XmlUtil.converyToJavaBean(xml, TextMessage.class);
		System.out.println(message.getContent());
	
		
	}
	
}