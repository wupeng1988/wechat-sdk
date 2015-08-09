package org.dptech.wx.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	public static String default_charset = "UTF-8";
	
	public static String get(String url, String param) throws IOException{
		return get(url, param, default_charset);
	}
	
	public static String get(String url, String param, String encoding) throws IOException{
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setReadTimeout(30000);
		commonHeaders(conn);
		
		if(!StringUtils.isEmpty(param)){
			OutputStream os = conn.getOutputStream();
			os.write(param.getBytes(encoding));
			os.close();
		}
		
		conn.connect();
		
		StringBuffer sb = new StringBuffer();
		int code = conn.getResponseCode();
		if(code != 200){
			logger.error("request url [ " + url + " ] returns code [ " + code + " ]");
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding)); 
			String msg = null;
			while((msg = br.readLine()) != null){
				sb.append(msg).append("\n");
			}
			
			if(sb.length() > 0){
				sb.deleteCharAt(sb.length() - 1);
			}
		} finally {
			if(br != null){
				br.close();
			}
		}
		
		return sb.toString();
	}
	
	
	private static void commonHeaders(HttpURLConnection conn) {
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.setRequestProperty("user-agent", 
        		"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
        		+ "Chrome/41.0.2272.76 Safari/537.36");
	}


	public static String post(String url, String param) throws IOException{
		return post(url, param, default_charset);
	}
	
	public static String post(String url, String param, String encoding) throws IOException{
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setReadTimeout(30000);
		commonHeaders(conn);
		
		if(!StringUtils.isEmpty(param)){
			OutputStream os = conn.getOutputStream();
			os.write(param.getBytes(encoding));
			os.close();
		}
		
		conn.connect();
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			String msg = null;
			while((msg = br.readLine()) != null){
				sb.append(msg).append("\n");
			}
			
			if(sb.length() > 0){
				sb.deleteCharAt(sb.length() - 1);
			}
		} finally {
			if(br != null)
				br.close();
		}
		
		return sb.toString();
	}
	
	public static String postJson(String url, String json) throws IOException{
		return postJson(url, json, default_charset);
	}
	
	public static String postJson(String url, String json, String encoding) throws IOException{
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setReadTimeout(30000);
		commonHeaders(conn);
		
		if(!StringUtils.isEmpty(json)){
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes(encoding));
			os.close();
		}
		
		conn.connect();
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			String msg = null;
			while((msg = br.readLine()) != null){
				sb.append(msg).append("\n");
			}
			
			if(sb.length() > 0){
				sb.deleteCharAt(sb.length() - 1);
			}
		} finally {
			if(br != null)
				br.close();
		}
		
		return sb.toString();
	}
	
	public static String toParams(Map<String, ? extends Object> map){
		if(map == null)
			return null;
		
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, ? extends Object> entry : map.entrySet()){
			sb.append(entry.getKey()).append("=").append(String.valueOf(entry.getValue())).append("&");
		}
		
		if(sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
}