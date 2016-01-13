package org.singledog.wechat.sdk.token;

import org.singledog.wechat.sdk.Interfaces;
import org.singledog.wechat.sdk.conf.CacheConfig;
import org.singledog.wechat.sdk.conf.WechatConfig;
import org.singledog.wechat.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * operations with token
 * Created by adam on 1/3/16.
 */
@Component
public class AccessTokenComponent {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenComponent.class);

    @Autowired
    public WechatConfig config;
    @Autowired
    public RestTemplate restTemplate;
    @Autowired
    public CacheConfig cacheConfig;
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 重新获取access_token
     *
     * @return
     */
    public AccessToken refreshToken() {
        String key = cacheConfig.getCachePrefix() + ".accessToken";
        ValueOperations valueOperations = redisTemplate.opsForValue();
        AccessToken token = (AccessToken) valueOperations.get(key);
        if (token != null)
            return token;

        String appid = config.getAppId();
        String secret = config.getAppSecret();

        StringBuilder sb = new StringBuilder(Interfaces.get_token.toString());
        sb.append("?").append("grant_type=client_credential&appid=").append(appid).append("&secret=").append(secret);

        String json = restTemplate.getForObject(sb.toString(), String.class);//HttpUtil.get(url, param);
        Map<String, Object> map = JsonUtil.toMap(json);
        if (map.containsKey("errcode")) {
            throw new RuntimeException("server response message : " + json);
        } else {
            token = new AccessToken();
            token.setAccess_token((String) map.get("access_token"));
            token.setExpires_in((int) map.get("expires_in"));
            valueOperations.set(key, token, token.getExpires_in() - 10, TimeUnit.SECONDS);
            return token;
        }
    }
}
