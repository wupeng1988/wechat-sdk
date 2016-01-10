package org.dptech.simple_test;

import java.io.IOException;

import org.dptech.wx.sdk.model.push.TextMessage;
import org.dptech.wx.sdk.util.BeanUtil;
import org.dptech.wx.sdk.util.HttpUtil;
import org.dptech.wx.sdk.util.XmlUtil2;

public class Test {
	
	@org.junit.Test
	public void test() throws IOException{
		
		String url = "http://api.map.baidu.com/geocoder/v2/";
		System.out.println(HttpUtil.get(url, "address=保定站&output=json"
				+ "&ak=HL8GH0LIFsqlapbTGSHpGI1b"));
		
		url = "http://api.map.baidu.com/geocoder/v2/";
		System.out.println(HttpUtil.get(url, "ak=HL8GH0LIFsqlapbTGSHpGI1b"
				+ "&location=38.868749313739,115.48722173328"
				+ "&output=json&pois=1"));
		System.out.println(HttpUtil.get(url, "ak=HL8GH0LIFsqlapbTGSHpGI1b"
				+ "&location=38.872301512728,115.60725969349"
				+ "&output=json&pois=1"));
	}
	
	
	public static void main(String[] args) {
		
		String xml = " <xml> "
				+" <ToUserName><![CDATA[toUser]]></ToUserName> "
				+" <FromUserName><![CDATA[fromUser]]></FromUserName> " 
				+" <CreateTime>1348831860</CreateTime> "
				+" <MsgType><![CDATA[text]]></MsgType> "
				+" <Content><![CDATA[this is a test]]></Content> "
				+" <MsgId>1234567890123456</MsgId> "
				+" </xml> ";
		
		
		TextMessage textMessage = BeanUtil.mapToBean(XmlUtil2.toMap(xml), TextMessage.class);
		System.out.println(textMessage.getContent());
		
		long start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++){
//			XmlUtil2.toMap(xml);
			BeanUtil.mapToBean(XmlUtil2.toMap(xml), TextMessage.class);
		}
		System.out.println("xmlutil2 to map costtime : " + (System.currentTimeMillis() - start));
		
	}
	
}