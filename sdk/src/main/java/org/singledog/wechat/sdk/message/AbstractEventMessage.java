package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 1/2/16.
 */
public class AbstractEventMessage extends AbstractMessage {

    private String Event;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }
}
