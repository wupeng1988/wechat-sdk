package org.singledog.wechat.sdk.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * cache configuration
 *
 *
 * Created by adam on 1/3/16.
 */
@Configuration
public class CacheConfig {

    @Value("${org.singledog.wechat.cache.prefix:singledog}")
    private String cachePrefix;

    @Value("${org.singledog.wechat.cache.defaultExpiration:0}")
    private long defaultExpiration;

    @Autowired
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory factory) {
        final RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new CustomSerializationRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new CustomSerializationRedisSerializer());
        return template;
    }

    @Autowired
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        cacheManager.setCachePrefix(new RedisCachePrefix() {
            @Override
            public byte[] prefix(String cacheName) {
                return cachePrefix.getBytes();
            }
        });
        cacheManager.setDefaultExpiration(defaultExpiration);
        return cacheManager;
    }

    public String getCachePrefix() {
        return cachePrefix;
    }

    public long getDefaultExpiration() {
        return defaultExpiration;
    }
}
