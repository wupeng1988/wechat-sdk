package org.singledog.wechat.sdk.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * configuration
 *
 * Created by adam on 1/3/16.
 */
@Configuration
public class WechatConfig {

    @Value("${org.singledog.wechat.token}")
    private String token;
    @Value("${org.singledog.wechat.appId}")
    private String appId;
    @Value("${org.singledog.wechat.appSecret}")
    private String appSecret;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
