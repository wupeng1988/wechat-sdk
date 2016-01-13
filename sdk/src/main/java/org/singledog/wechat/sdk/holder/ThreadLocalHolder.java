package org.singledog.wechat.sdk.holder;

import java.io.InputStream;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
public class ThreadLocalHolder {

    private static final ThreadLocal<String> originalXml = new ThreadLocal<>();
    private static final ThreadLocal<InputStream> inputStream = new ThreadLocal<>();

    public static void setOriginalXml(String xml) {
        originalXml.set(xml);
    }

    public static String getOriginalXml() {
        return originalXml.get();
    }

    public static void setInputStream(InputStream is) {
        inputStream.set(is);
    }

    public static InputStream getInputStream() {
        return inputStream.get();
    }

}
