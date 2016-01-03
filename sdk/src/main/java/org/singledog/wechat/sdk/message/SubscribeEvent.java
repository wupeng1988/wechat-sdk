package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 1/2/16.
 */
public class SubscribeEvent extends AbstractEventMessage {

    private String EventKey;

    private String Ticket;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
