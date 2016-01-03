package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 1/2/16.
 */
public class ViewEvent extends AbstractEventMessage {

    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
