package org.dptech.wx.sdk.exception;

import java.io.IOException;
import java.util.Map;

import org.dptech.wx.sdk.util.JsonUtil;

public class ErrorCheckUtil {
	
	public static void check(String json){
		Map<String, Object> map;
		try {
			map = JsonUtil.toMap(json);
			if(map.containsKey("errcode")){
				int code = (int) map.get("errcode");
				if(code == 40001 || code == 40014 || code == 42001){
					throw new TokenExpireException("token error ！ json : " + json);
				}
				if(code != 0)
					throw new RuntimeException("server response message : " + json);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
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