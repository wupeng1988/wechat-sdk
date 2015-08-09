package org.dptech.simple_test;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import org.dptech.wx.sdk.WeChatConfig;
import org.dptech.wx.sdk.cfg.AutoReplyMessageConfig;

public class MessageConfigTest {

	@BeforeClass
	public static void setUp() throws IOException{
		WeChatConfig.config("D:/java/workspace_luna/wx/src/main/resources/wx/wechat.properties");
	}
	
	@Test
	public void test(){
		System.out.println(AutoReplyMessageConfig.getReplyMessage("text", "123"));
		System.out.println(AutoReplyMessageConfig.getReplyMessage("text", "北京到南京"));
		
	}
	
}