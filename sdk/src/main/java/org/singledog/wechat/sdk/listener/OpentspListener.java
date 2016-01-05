package org.singledog.wechat.sdk.listener;

import org.singledog.wechat.sdk.Order;
import org.singledog.wechat.sdk.event.OpentspEvent;

/**
 * @author wupeng
 * @version 1.0
 * @date 2015-09-29
 * @modify
 * @copyright Navi Tsp
 */
public interface OpentspListener<T extends OpentspEvent<?>> extends Order {

    /**
     * true 表示异步， false表示同步
     * @return
     */
    public boolean async();

    public void onEvent(T event);

}
