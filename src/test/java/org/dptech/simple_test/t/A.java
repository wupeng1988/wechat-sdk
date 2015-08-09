package org.dptech.simple_test.t;

import java.lang.reflect.ParameterizedType;

public class A<T> {

	public Class getGenericClass() {
		return (Class<T>) ((ParameterizedType) (getClass()
				.getGenericSuperclass())).getActualTypeArguments()[0];
	}

	public void hello() {
		System.out.println(this.getGenericClass().getName());
	}

}
