package org.singledog.wechat.sdk.message;

/**
 *
 * @see http://mp.weixin.qq.com/wiki/7/9f89d962eba4c5924ed95b513ba69d9b.html
 *
 * Created by adam on 1/2/16.
 */
public enum EventTypes {

    subscribe(SubscribeEvent.class),


    unsubscribe(UnSubscribeEvent.class),


    SCAN(ScanEvent.class),


    LOCATION(LocationEvent.class),


    CLICK(ClickEvent.class),


    VIEW(ViewEvent.class);

    private Class<?> mappingClass;

    private EventTypes(Class<?> mappingClass) {
        this.mappingClass = mappingClass;
    }

    public Class<?> getMappingClass() {
        return mappingClass;
    }

}
