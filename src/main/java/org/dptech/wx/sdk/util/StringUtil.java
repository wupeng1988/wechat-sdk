package org.dptech.wx.sdk.util;

import org.springframework.util.StringUtils;

public class StringUtil {
	
	public static boolean isDigit(String str){
		if(StringUtils.isEmpty(str))
			return false;
		
		str = str.trim();
		
		if(StringUtils.isEmpty(str))
			return false;
		
		int j = 0;
		for(int i = 0; i < str.length(); i++){
			char ch = str.charAt(i);
			if('.' == ch){
				if(j >= 1)
					return false;
				j++;
				continue;
			}
			if(!Character.isDigit(ch)){
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isEmpty(String ...strs){
		if(strs == null || strs.length == 0){
			return true;
		}
		
		for(String str : strs){
			if(StringUtils.isEmpty(str)){
				return true;
			}
		}
		
		
		return false;
	}
	
}