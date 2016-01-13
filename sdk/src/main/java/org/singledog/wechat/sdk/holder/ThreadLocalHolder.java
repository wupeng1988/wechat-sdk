package org.singledog.wechat.sdk.holder;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
public class ThreadLocalHolder {

    private static final ThreadLocal<String> originalXml = new ThreadLocal<>();

    public static void setOriginalXml(String xml) {
        originalXml.set(xml);
    }

    public static String getOriginalXml() {
        return originalXml.get();
    }

}
