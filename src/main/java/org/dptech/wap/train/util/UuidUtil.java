package org.dptech.wap.train.util;

import java.util.UUID;

public class UuidUtil {
	
	public static String randomUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	public static void main(String[] args) {
		System.out.println(randomUUID());
	}
	
}
