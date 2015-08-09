package org.dptech.wx.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 微信配置
 * 
 * @author wupeng
 *
 */
public class WeChatConfig {
	
	public static final String k_appid = "appid";
	public static final String k_appSecret = "appSecret";
	public static final String k_multiServer = "multiServer";
	public static final String k_config_dir = "config_dir";
	
	/**
	 * 开发者中心填写的token
	 */
	public static final String k_token = "token";
	/**
	 * 开发者中心填写的encodingAESKey
	 */
	public static final String k_aesKey = "encodingAESKey";
	/**
	 * 模式，是否为安全模式
	 */
	public static final String k_safeMode = "msg.safeMode";
	
	private static final Properties props = new Properties();
	
	public static void setIsMultiServer(boolean multi){
		props.setProperty(k_multiServer, multi ? "true" : "false");
	}
	
	public static boolean isMultiServer(){
		return "true".equals(props.getProperty(k_multiServer));
	}
	
	public synchronized static void config(String path) throws IOException{
		InputStream is = null;
		File file = new File(path);
		try {
			is = new FileInputStream(file);
			props.clear();
			props.load(is);
		} finally {
			if(is != null)
				is.close();
		}
		
		String configDir = props.getProperty(k_config_dir);
		if(configDir == null){
			configDir = file.getParent();
			props.setProperty(k_config_dir, configDir);
		}
		
		file = null;
	}
	
	public static String getValue(String key){
		return props.getProperty(key);
	}
	
}