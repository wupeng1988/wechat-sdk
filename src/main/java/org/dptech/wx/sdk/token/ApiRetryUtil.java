package org.dptech.wx.sdk.token;

import java.io.IOException;

import redis.clients.jedis.JedisCommands;

import org.dptech.wx.sdk.common.AccessTokenCallback;
import org.dptech.wx.sdk.exception.TokenExpireException;

public class ApiRetryUtil {
	/**
	 * 自动重试
	 * 
	 * @param callback
	 * @param jedis
	 * @return
	 * @throws IOException
	 */
	public static <T> T doWithToken(AccessTokenCallback<T> callback, JedisCommands jedis) throws IOException{
		if(jedis != null)
			return doMultiple(callback, jedis);
		else
			return doSingle(callback);
	}
	
	private static <T> T doSingle(AccessTokenCallback<T> callback){
		String token = TokenHolder.getToken();
		T t = null;
		try {
			callback.withAccessToken(token);
		} catch (TokenExpireException e) {
			e.printStackTrace();
			TokenHolder.refreshToken();
			token = TokenHolder.getToken();
			t = callback.withAccessToken(token);
		}
		
		return t;
	} 
	
	private static <T> T doMultiple(AccessTokenCallback<T> callback, JedisCommands jedis) throws IOException{
		String token = TokenHolder2M.getToken(jedis);
		if(token == null || "".equals(token)){
			return null;
		}
		
		T t = null;
		
		try {
			t = callback.withAccessToken(token);
		} catch (TokenExpireException e) {
			e.printStackTrace();
			String tmptoken = TokenHolder2M.getToken(jedis);
			if(token.equals(tmptoken)){
				TokenHolder2M.refreshToken(jedis);
				tmptoken = TokenHolder2M.getToken(jedis);
			}
			
			t = callback.withAccessToken(tmptoken);
		}
		
		return t;
	}
	
}