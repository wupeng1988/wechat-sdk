package org.dptech.wx.sdk.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author lichao
 * 获取properties文件内容的工具类
 * 加载配置文件，同时返回指定的key,如果指定的key不存在，就会重新加载配置文件（适合于动态的固定key的配置文件加载）
 */
public class PropertiesUtil {
	//存放配置文件的所有的key-value
	private static Map<String,String> allParam = new HashMap<String, String>();
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	//根据文件名称-key，返回相应key的值
    public static String getPropertiesByKey(String fileName,String key){
		try {
			 if(allParam.containsKey(key)){
				return allParam.get(key);
			 }else{
				 logger.info("开始初始化配置文件【"+fileName+"】");
				 allParam.clear();
		   		 InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
		       	 Properties p = new Properties();
		       	 p.load(in);
		       	 Set<Entry<Object, Object>> allKey = p.entrySet();
		       	 for (Entry<Object, Object> entry : allKey) {
					allParam.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
				 }
		       	 in.close();
		       	 logger.info("成功初始化配置文件【"+fileName+"】");
		       	 return allParam.get(key);
			 }
	   	} catch (Exception e) {
	   		logger.error("初始化配置文件【"+fileName+"】出错");
	   		e.printStackTrace();
	    }
		return null;
    }
}