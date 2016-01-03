package org.singledog.wechat.sdk.message;


/**
 * @see http://mp.weixin.qq.com/wiki/17/f298879f8fb29ab98b2f2971d42552fd.html
 *
 *
 * Created by adam on 1/2/16.
 */
public enum MessageTypes {

    /**
     * text message
     */
    text(TextMessage.class),

    /**
     * image
     */
    image(ImageMessage.class),

    /**
     * voice
     */
    voice(VoiceMessage.class),

    /**
     *
     */
    video(VideoMessage.class),

    /**
     *
     */
    shortvideo(ShortVideoMessage.class),

    /**
     *
     */
    location(LocationMessage.class),

    /**
     *
     */
    link(LinkMessage.class),


    event(AbstractEventMessage.class);


    private Class<? extends AbstractMessage> mappingClass;

    private MessageTypes(Class<? extends AbstractMessage> mappingClass) {
        this.mappingClass = mappingClass;
    }

    public Class<? extends AbstractMessage> getMappingClass() {
        return mappingClass;
    }

}
