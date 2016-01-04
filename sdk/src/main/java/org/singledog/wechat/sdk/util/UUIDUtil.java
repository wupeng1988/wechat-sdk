package org.singledog.wechat.sdk.util;

import java.util.UUID;

public class UUIDUtil {
	
	public static String randomUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(randomUUID());
	}

}
