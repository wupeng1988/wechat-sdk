package org.singledog.wechat.sdk.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import ognl.Ognl;
import ognl.OgnlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	
	private static Map<String, PropertyDescriptor[]> class_infos = new ConcurrentHashMap<>();
	private static final Object[] emptyArray = new Object[0];

	public static <T> T mapToBeanByJson(Map<String, ? extends Object> map, Class<T> clazz){
		try {
			return JsonUtil.fromJson(JsonUtil.toJson(map), clazz);
		} catch (JsonProcessingException e) {
			throw new BeanInstantiationException(clazz, "json convert failed ! ");
		}
	}

    public static void navigateFields(Object bean, FieldNavigator navigator) {
        PropertyDescriptor[] propertyDescriptors = getClassPropertyDescriptors(bean.getClass(), false);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String name = propertyDescriptor.getName();
            if("class".equals(name))
                continue;
            Object value = null;
            try {
                Method method = propertyDescriptor.getReadMethod();
                if (method != null) {
                    method.setAccessible(true);
                    value = method.invoke(bean, emptyArray);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    value = Ognl.getValue(name, bean);
                } catch (OgnlException e1) {
                    e1.printStackTrace();
                }
            }

            navigator.onField(name, value);
        }
    }

	public static PropertyDescriptor[] getClassPropertyDescriptors(Class<?> clazz, boolean cascade){
		String className = clazz.getName();
		if(class_infos.containsKey(className)){
			return class_infos.get(className);
		}

		PropertyDescriptor[] properties = BeanUtils.getPropertyDescriptors(clazz);
        List<PropertyDescriptor> propertyDescriptorList = new ArrayList<>(Arrays.asList(properties));

        if (cascade) {
            Class superClazz = clazz.getSuperclass();
            if (superClazz != null && superClazz != Object.class && !superClazz.isInterface()) {
                properties = getClassPropertyDescriptors(superClazz, cascade);
                propertyDescriptorList.addAll(Arrays.asList(properties));
            }
        }

        properties = propertyDescriptorList.toArray(new PropertyDescriptor[0]);
        class_infos.put(className, properties);
		return properties;
	}

	public static <T> T mapToBean(Map<String, ? extends Object> map, Class<T> clazz){
		String className = clazz.getName();
		try {
			T instance = clazz.newInstance();
			PropertyDescriptor[] props = getClassPropertyDescriptors(clazz, false);
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

    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        navigateFields(bean, new FieldNavigator() {
            @Override
            public void onField(String fieldName, Object value) {
                map.put(fieldName, value);
            }
        });

        return map;
    }
	
	public static <T> void copyAttrs4Update(T dest, T source){
		PropertyDescriptor[] props = getClassPropertyDescriptors(dest.getClass(), false);
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

    public static interface FieldNavigator {
        public void onField(String fieldName, Object value);
    }

}