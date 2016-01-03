package org.singledog.wechat.sdk.conf;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by adam on 1/3/16.
 */
public class CustomSerializationRedisSerializer extends JdkSerializationRedisSerializer {

    private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    @Override
    public Object deserialize(byte[] bytes) {
        try {
            return super.deserialize(bytes);
        } catch (SerializationException exception) {
            return stringRedisSerializer.deserialize(bytes);
        }
    }


}
