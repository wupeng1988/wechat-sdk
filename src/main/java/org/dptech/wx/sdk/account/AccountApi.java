package org.dptech.wx.sdk.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dptech.wx.sdk.common.Interfaces;
import org.dptech.wx.sdk.exception.ErrorCheckUtil;
import org.dptech.wx.sdk.util.HttpUtil;
import org.dptech.wx.sdk.util.JsonUtil;


public class AccountApi {
	
	private AccountApi(){}
	
	private static final AccountApi api = new AccountApi();
	
	public static AccountApi getInstance(){
		return api;
	}
	
	public String shortUrl(String accessToken, String longUrl) {
		try {
			String url = Interfaces.short_url.toString()+"?access_token="+accessToken;
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("long_url", longUrl);
			paramMap.put("action", "long2short");
			
			String json = HttpUtil.postJson(url, JsonUtil.toJson(paramMap));
			
			Map<String, Object> map = JsonUtil.toMap(json);
			ErrorCheckUtil.check(json, map);
			Object errcode = map.get("errcode");
			if(errcode != null && errcode.equals(0)){
				return (String) map.get("short_url");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return longUrl;
	}
}