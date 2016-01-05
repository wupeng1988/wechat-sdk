package org.singledog.wechat.sdk.event;

import org.singledog.wechat.sdk.util.UUIDUtil;

/**
 * @author wupeng
 * @version 1.0
 * @date 2015-10-16
 * @modify
 * @copyright Navi Tsp
 */
public abstract class AbstractOpentspEvent<T> implements OpentspEvent<T> {
    @Override
    public String getEventId() {
        return UUIDUtil.randomUUID();
    }
}
