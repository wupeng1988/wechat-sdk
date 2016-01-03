package org.singledog.wechat.sdk.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final ObjectMapper mapperExcludeNull = new ObjectMapper();
	
	static {
		mapperExcludeNull.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	
	public static String toJson(Object obj) throws JsonProcessingException{
		return toJson(obj, mapper);
	}
	private static String toJson(Object obj, ObjectMapper objectMapper) throws JsonProcessingException {
		if(obj == null)
			return null;
		return objectMapper.writeValueAsString(obj);
	}
	
	public static String toJsonIgnoreNull(Object obj) throws JsonProcessingException{
		return toJson(obj, mapperExcludeNull);
	}
	
	public static void toJsonIgnoreNull(Object obj, OutputStream os) throws IOException {
		if(obj != null){
			mapperExcludeNull.writeValue(os, obj);
		}
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