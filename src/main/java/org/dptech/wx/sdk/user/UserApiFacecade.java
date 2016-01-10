package org.dptech.wx.sdk.user;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCommands;

import org.dptech.wx.sdk.common.AccessTokenCallback;
import org.dptech.wx.sdk.exception.TokenExpireException;
import org.dptech.wx.sdk.model.UserInfo;
import org.dptech.wx.sdk.token.ApiRetryUtil;

public class UserApiFacecade {
	
	private static Logger logger = LoggerFactory.getLogger(UserApiFacecade.class);
	
	private static UserApiFacecade inst = new UserApiFacecade();
	
	private UserApiFacecade(){}
	
	public static UserApiFacecade getInstance(){
		return inst;
	}
	
	public UserInfo getUserInfo(JedisCommands jedis, final String openId){
		UserInfo userInfo = null;
		try {
			userInfo = ApiRetryUtil.doWithToken(new AccessTokenCallback<UserInfo>() {
				@Override
				public UserInfo withAccessToken(String access_token) throws TokenExpireException {
					
					return UserAPI.getInstance().getUserInfo(access_token, openId, null);
				}
				
			}, jedis);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return userInfo;
	}
	
}