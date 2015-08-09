package org.dptech.wx.sdk.util;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

public class BeanUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	
	private static Map<String, PropertyDescriptor[]> class_infos = new ConcurrentHashMap<>();
	
	public static <T> T mapToBeanByJson(Map<String, ? extends Object> map, Class<T> clazz){
		try {
			return JsonUtil.fromJson(JsonUtil.toJson(map), clazz);
		} catch (JsonProcessingException e) {
			throw new BeanInstantiationException(clazz, "json convert failed ! ");
		}
	}
	
	
	public static PropertyDescriptor[] getClassPropertyDescriptors(Class<?> clazz){
		String className = clazz.getName();
		if(class_infos.containsKey(className)){
			return class_infos.get(className);
		}
		
		PropertyDescriptor[] properties = BeanUtils.getPropertyDescriptors(clazz);
		class_infos.put(className, properties);
		return properties;
	}
	
	public static <T> T mapToBean(Map<String, ? extends Object> map, Class<T> clazz){
		String className = clazz.getName();
		try {
			T instance = clazz.newInstance();
			PropertyDescriptor[] props = getClassPropertyDescriptors(clazz);
			for(PropertyDescriptor prop : props){
				String name = prop.getName();
				if("class".equals(name))
					continue;
				Object value = map.get(name);
				if(value == null){
					String tmp = StringUtils.capitalize(name);
					value = map.get(tmp);
				}
				if(value != null){
					try {
						Ognl.setValue(name, instance, value);
					} catch (OgnlException e) {
						logger.error(e.getMessage(), e);
						throw new BeanInstantiationException(clazz, "ognl set property error ! [ " + className + "." + name);
					}
				}
			}
			
			return instance;
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new BeanInstantiationException(clazz, " can not instance class ! " + className);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new BeanInstantiationException(clazz, " can not access to class [ " + className + " ] !");
		}
	}
	
	public static <T> void copyAttrs4Update(T dest, T source){
		PropertyDescriptor[] props = getClassPropertyDescriptors(dest.getClass());
		for(PropertyDescriptor prop : props){
			String name = prop.getDisplayName();
			if("class".equals(name))
				continue;
			
			try {
				Object val = Ognl.getValue(name, source);
				if(val != null && !"".equals(val)){
					Ognl.setValue(name, dest, val);
				}
			} catch (OgnlException e) {
				logger.error(e.getMessage(), e);
				throw new FatalBeanException(e.getMessage(), e);
			}
		}
	}

}