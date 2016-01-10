package org.dptech.wx.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext context;
	
	private static final Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);
	
	@Override
	public void setApplicationContext(ApplicationContext contexts)
			throws BeansException {
		
		context = contexts;
		logger.info("set application context complete !");
	}
	
	public static <T> T getBean(String name, Class<T> clazz){
		T t = null;
		
		//优先按照名称获取， 名称获取不到再按照类型获取
		try {
			t = getBeanByName(name, clazz);
		} catch (Exception e){
			logger.info(e.getMessage(), e);
		}
		
		if(t == null){
			try {
				t = getBeanByType(clazz);
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
		}
		if(t == null){
			logger.error("can not found required bean !", 
					new BeanCreationException(new StringBuffer("can not find bean [ ").append(name).append(" ] ! ").toString()));
		}
		
		return t;
	}
	
	public static <T> T getBeanByType(Class<T> clazz){
		T t = context.getBean(clazz);
		if(t == null){
			logger.error("can not found required bean !", 
					new BeanCreationException(new StringBuffer("can not find bean by type [ ").append(clazz.getName()).append(" ] ! ").toString()));
		}
		return t;
	}
	
	public static <T> T getBeanByName(String name, Class<T> clazz){
		T t = context.getBean(name, clazz);
		if(t == null){
			logger.error("can not found required bean !", 
					new BeanCreationException(new StringBuffer("can not find bean by name [ ").append(name).append("] for class").append(clazz.getName())
							.append(" ] ! ").toString()));
		}
		return t;
	}
	
}