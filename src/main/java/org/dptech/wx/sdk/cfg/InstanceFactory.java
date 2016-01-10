package org.dptech.wx.sdk.cfg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dptech.wx.sdk.util.SpringContextUtil;

public class InstanceFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(InstanceFactory.class);
	
	private static final Map<String, Object> class_instance = new ConcurrentHashMap<>();
	private static final Map<String, Object> spring_instance = new ConcurrentHashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstanceByClass(Class<T> clazz){
		String className = clazz.getName();
		if(class_instance.containsKey(className)){
			return (T) class_instance.get(className);
		}
		
		T handler = null;
		
		try {
			handler = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("can not create instance from className : " + className);
		}
		
		class_instance.put(className, handler);
		return handler;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstanceFromSpring(String beanName, Class<T> clazz){
		if(spring_instance.containsKey(beanName)){
			return (T) spring_instance.get(beanName);
		}
		
		T handler = null;
		try {
			handler = SpringContextUtil.getBean(beanName, clazz);
		} catch(Exception e){
			logger.error("can not found instance from spring : " + beanName);
		}
		
		spring_instance.put(beanName, handler);
		return handler;
	}
	
}