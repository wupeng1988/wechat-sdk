package org.dptech.wx.sdk.token;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import redis.clients.jedis.JedisCommands;

import org.dptech.wx.sdk.util.JsonUtil;

/**
 * 多台服务器使用此类
 * 
 * @author wupeng
 *
 */
public class TokenHolder2M {

	private static final String token_key = "atoken";
	
	private static final String js_token_key = "js_token";  
	
	private static final String wechat_module = "wchat";
	
	private static Lock lock = new ReentrantLock();
	
	/**
	 * 同步获取access_token
	 * @param jedis
	 * @return
	 * @throws IOException
	 */
	public static synchronized String getToken(JedisCommands jedis) throws IOException {
		AccessToken token = null;
		
		String json = jedis.hget(wechat_module, token_key);
		if(json == null || "".equals(json)){
			token = AccessTokenAPI.getInstance().refreshToken();
			if(token != null){
				String json2 = JsonUtil.toJson(token);
				jedis.hset(wechat_module, token_key, json2);
				jedis.expire(wechat_module, token.getExpires_in() - 30*60);
			}
		} else {
			Map<String, Object> map = JsonUtil.toMap(json); 
			token = new AccessToken();
			token.setAccess_token((String) map.get("access_token"));
			token.setExpires_in((int) map.get("expires_in"));
		}
		
		if(token != null)
			return token.getAccess_token();
		
		return null;
	}
	
	/**
	 * 同步获取js签名
	 * @param jedis
	 * @param key
	 * @return
	 * @throws IOException
	 *//*
	public static JSSignature getJsSignature(JedisCommands jedis, String key) throws IOException {
		JSSignature token = null;
		
		String json = jedis.get(js_token_key + key);
		if(json == null || "".equals(json)){
			lock.lock();// 得到锁
			try{
				token = AccessTokenAPI.getInstance().refreshJsApiTicket(key, getToken(jedis));
			}finally {  
	            lock.unlock();// 释放锁  
	        }
			if(token != null){
				String json2 = JsonUtil.toJson(token);
				jedis.set(js_token_key + key, json2);
				jedis.expire(js_token_key + key, token.getExpires_in() - 30*60);
			}
		} 
		else {
			Map<String, Object> map = JsonUtil.toMap(json); 
			token = new JSSignature();
			
			token.setNoncestr((String)map.get("noncestr"));
			token.setSignature((String)map.get("signature"));
			token.setTimestamp((Long)map.get("timestamp"));
			token.setExpires_in((int) map.get("expires_in"));
			token.setAppId((String)map.get("appId"));
		}
		
		return token;
	}*/
	
	public static boolean refreshToken(JedisCommands jedis){
		jedis.hdel(wechat_module, token_key);
		try {
			getToken(jedis);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}