package org.singledog.wechat.sdk.exception;

import org.singledog.wechat.sdk.util.JsonUtil;

import java.util.Map;

public class ErrorCheckUtil {
	
	public static void check(String json){
		check(json, JsonUtil.toMap(json));
	}
	
	public static void check(String json, Map<String, Object> map){
		if(map.containsKey("errcode")){
			int code = (int) map.get("errcode");
			if(code == 40001 || code == 40014 || code == 42001){
				throw new TokenExpireException("token error ！ json : " + json);
			}
			if(code != 0)
				throw new RuntimeException("server response message : " + json);
		}
	}
	
}