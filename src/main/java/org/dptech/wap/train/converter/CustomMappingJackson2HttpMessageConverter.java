package org.dptech.wap.train.converter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter{
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	static {
		mapper.setSerializationInclusion(Include.NON_NULL);
	}
	
	public CustomMappingJackson2HttpMessageConverter() {
		super();
		super.setObjectMapper(mapper);
	}
}
