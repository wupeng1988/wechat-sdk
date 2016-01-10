package org.dptech.wx.sdk.util;

import java.util.Map;
import java.util.Set;

public class WxUtil {
	
	public static String replaceVariables(String text, Map<String, String> map){
		Set<Map.Entry<String, String>> set = map.entrySet();
		for(Map.Entry<String, String> entry : set){
			String key = new StringBuffer("#{").append(entry.getKey()).append("}").toString();
			if(text.contains(key)){
				text.replace(key, entry.getValue());
			}
		}
		
		return text;
	}
	
}