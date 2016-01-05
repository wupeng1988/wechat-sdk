package org.singledog.wechat.sdk.listener;

import org.singledog.wechat.sdk.event.OpentspEvent;

/**
 * @author wupeng
 * @version 1.0
 * @date 2015-10-30
 * @modify
 * @copyright Navi Tsp
 */
public abstract class AbstractOpentspListener<T extends OpentspEvent<?>> implements OpentspListener<T> {
    @Override
    public boolean async() {
        return true;
    }

    @Override
    public int order() {
        return 0;
    }
}
