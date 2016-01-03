package org.singledog.wechat.sdk;

public enum Interfaces {
	
	get_token("https://api.weixin.qq.com/cgi-bin/token"),//获取token接口
	user_info("https://api.weixin.qq.com/cgi-bin/user/info"),
	short_url("https://api.weixin.qq.com/cgi-bin/shorturl"),//长连接转短连接接口
	custom_send("https://api.weixin.qq.com/cgi-bin/message/custom/send"),//发送客服消息
	follow_users("https://api.weixin.qq.com/cgi-bin/user/get"),//关注用户列表
	get_jsapi_ticket("https://api.weixin.qq.com/cgi-bin/ticket/getticket");//获取js_token
	
	private String url;
	
	private Interfaces(String url){
		this.url = url;
	}
	
	@Override
	public String toString() {
		return url;
	}
	
}