package org.dptech.wx.sdk.token;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dptech.wx.sdk.WeChatConfig;
import org.dptech.wx.sdk.common.Interfaces;
import org.dptech.wx.sdk.encrypt.CommonEncryptUtil;
import org.dptech.wx.sdk.util.HttpUtil;
import org.dptech.wx.sdk.util.JsonUtil;
import org.dptech.wx.sdk.util.Md5Util;

/**
 * token相关
 * 
 * @author wupeng
 *
 */
public class AccessTokenAPI {
	private static final Logger logger = LoggerFactory.getLogger(AccessTokenAPI.class);
	private static final AccessTokenAPI api = new AccessTokenAPI();
	
	private AccessTokenAPI(){}
	
	public static AccessTokenAPI getInstance(){
		return api;
	}
	
	/**
	 * 重新获取access_token
	 * @return
	 */
	AccessToken refreshToken(){
		String appid = WeChatConfig.getValue(WeChatConfig.k_appid);
		String secret = WeChatConfig.getValue(WeChatConfig.k_appSecret);
		
		String url = Interfaces.get_token.toString();
		String param = "grant_type=client_credential&appid="+appid+"&secret="+secret;
		try {
			String json = HttpUtil.get(url, param);
			Map<String, Object> map = JsonUtil.toMap(json);
			if(map.containsKey("errcode")){
				throw new RuntimeException("server response message : " + json);
			} else {
				AccessToken token = new AccessToken();
				token.setAccess_token((String) map.get("access_token"));
				token.setExpires_in((int) map.get("expires_in"));
				return token;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 重新获取js sdk签名
	 * @param url 
	 * @param accessToken
	 * @return
	 */
	JSSignature refreshJsApiTicket(String url, String accessToken){
		String ticketurl = Interfaces.get_jsapi_ticket.toString();
		String param = "access_token=" + accessToken + "&type=jsapi";
		long timestamp = System.currentTimeMillis();
		String noncestr = Md5Util.md5(timestamp + "");
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("timestamp", timestamp + "");
		paramsMap.put("noncestr", noncestr);
		paramsMap.put("url", url);
		try { 
			String json = HttpUtil.get(ticketurl, param);
			Map<String, Object> map = JsonUtil.toMap(json);
			if(!json.contains("\"errcode\":0")){
				throw new RuntimeException("server response message : " + json);
			} else {
				String jsapi_ticket = (String) map.get("ticket");
				paramsMap.put("jsapi_ticket", jsapi_ticket);
				String[] params = new String[]{"timestamp", "jsapi_ticket", "noncestr", "url"};
//				String[] params = new String[]{timestamp + "", jsapi_ticket, noncestr, url};
				Arrays.sort(params);
				
				StringBuffer sb = new StringBuffer();
				for(String s:params){
					sb.append(s + "=" + paramsMap.get(s) + "&");
				}
				
				String auth = CommonEncryptUtil.SHA1(sb.toString().substring(0, sb.toString().length()-1));
				
				JSSignature signature = new JSSignature();
				signature.setNoncestr(noncestr);
				signature.setSignature(auth);
				signature.setTimestamp(timestamp);
				signature.setExpires_in((int) map.get("expires_in"));
				signature.setAppId(WeChatConfig.getValue(WeChatConfig.k_appid));
				return signature;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return null;
	}
	
}