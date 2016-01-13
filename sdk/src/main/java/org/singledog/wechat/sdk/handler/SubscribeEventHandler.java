package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.conf.WechatConfig;
import org.singledog.wechat.sdk.message.AbstractMessage;
import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.SubscribeEvent;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.user.UserComponent;
import org.singledog.wechat.sdk.user.UserInfo;
import org.singledog.wechat.sdk.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * triggered when user subscribe this account
 * <p/>
 * Created by adam on 1/3/16.
 */
@Component
public class SubscribeEventHandler implements MessageHandler<SubscribeEvent>, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SubscribeEventHandler.class);

    @Value("${wechat.sdk.onsubscribe.content}")
    private String subscribeConfigContent;
    @Value("${wechat.sdk.onsubscribe.class}")
    private String subscribeConfigClass;
    @Value("${wechat.sdk.chinese.hex:false}")
    private boolean hexCode;
    @Autowired
    private WechatConfig wechatConfig;

    private Class<? extends WeChatMessage> realClass;

    @Autowired
    private MongoTemplate userRepository;
    @Autowired
    private UserComponent userComponent;

    @Override
    public Class<SubscribeEvent> support() {
        return SubscribeEvent.class;
    }

    @Override
    public WeChatMessage handle(SubscribeEvent message) {
        if (wechatConfig.isAuthenticated()) {
            String openId = message.getFromUserName();
            UserInfo userInfo = userComponent.getUserInfo(openId, null);
            userInfo.setId(UUIDUtil.randomUUID());
            userRepository.save(userInfo);
        }

        try {
            String json = FileUtil.readString(subscribeConfigContent);
            try {
                if (hexCode)
                    json = new String(HexUtils.fromHexString(json), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }

            Map<String, Object> beanMap = BeanUtil.beanToMap(message);
            json = FileUtil.replace(json, beanMap);
            WeChatMessage result = BeanUtil.mapToBean(JsonUtil.toMap(json), realClass);
            if (result instanceof AbstractMessage) {
                ((AbstractMessage) result).setFromUserName(message.getToUserName());
                ((AbstractMessage) result).setToUserName(message.getFromUserName());
                ((AbstractMessage) result).setCreateTime(System.currentTimeMillis());
            }
            return result;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        realClass = (Class<? extends WeChatMessage>) Class.forName(subscribeConfigClass);
        FileUtil.readString(subscribeConfigContent);
    }
}
