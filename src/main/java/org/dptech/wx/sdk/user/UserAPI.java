package org.dptech.wx.sdk.user;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dptech.wx.sdk.common.Interfaces;
import org.dptech.wx.sdk.exception.ErrorCheckUtil;
import org.dptech.wx.sdk.model.UserInfo;
import org.dptech.wx.sdk.util.HttpUtil;
import org.dptech.wx.sdk.util.JsonUtil;

public class UserAPI {
	private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);
	private static UserAPI userApi = new UserAPI();
	
	private UserAPI(){}
	
	public static UserAPI getInstance(){
		return userApi;
	}
	
	/**
	 * 
	 * 
	 * @param access_token
	 * @param openId
	 * @param lang 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return
	 * @throws IOException 
	 */
	public UserInfo getUserInfo(String access_token, String openId, String lang) {
		String url = Interfaces.user_info.toString();
		StringBuffer params = new StringBuffer("access_token=").append(access_token).append("&openid=").append(openId);
		if(lang != null && !"".equals(lang)){
			params.append("&lang=").append(lang);
		}
		
		try {
			String json = HttpUtil.get(url, params.toString());
			ErrorCheckUtil.check(json);
			UserInfo info = JsonUtil.fromJson(json, UserInfo.class);
			return info;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
}