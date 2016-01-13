package org.singledog.wechat.sdk.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * configuration
 * <p/>
 * Created by adam on 1/3/16.
 */
@Configuration
public class WechatConfig {

    @Value("${wechat.sdk.token}")
    private String token;
    @Value("${wechat.sdk.appId}")
    private String appId;
    @Value("${wechat.sdk.appSecret}")
    private String appSecret;
    @Value("${wechat.sdk.authenticated}")
    private boolean authenticated;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
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

    public boolean isAuthenticated() {
        return authenticated;
    }
}
