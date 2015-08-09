package org.dptech.wx.sdk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	
	public static String toJson(Object obj) throws JsonProcessingException{
		if(obj == null)
			return null;
		return mapper.writeValueAsString(obj);
	}
	
	public static String toJsonIgnoreNull(Object obj) throws JsonProcessingException{
		Include include = mapper.getSerializationConfig().getSerializationInclusion();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String json = toJson(obj);
		mapper.setSerializationInclusion(include);
		return json;
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {
		if(json == null || "".equals(json)){
			return null;
		}
		T t = null;
		try {
			t = mapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return t;
	}
	
	public static <T> T fromJson(InputStream is, Class<T> clazz){
		if(is == null)
			return null;
		
		T t = null;
		try {
			t = mapper.readValue(is, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String json) throws IOException{
		return fromJson(json, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> toList(String json) throws IOException{
		return fromJson(json, List.class);
	}
	
}