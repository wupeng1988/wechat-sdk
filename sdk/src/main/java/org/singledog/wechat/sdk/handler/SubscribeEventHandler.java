package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.SubscribeEvent;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.user.UserComponent;
import org.singledog.wechat.sdk.user.UserInfo;
import org.singledog.wechat.sdk.user.UserRepository;
import org.singledog.wechat.sdk.util.BeanUtil;
import org.singledog.wechat.sdk.util.FileUtil;
import org.singledog.wechat.sdk.util.JsonUtil;
import org.singledog.wechat.sdk.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 *
 * triggered when user subscribe this account
 *
 * Created by adam on 1/3/16.
 */
@Component
public class SubscribeEventHandler implements MessageHandler<SubscribeEvent>, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SubscribeEventHandler.class);

    @Value("${wechat.sdk.onsubscribe.content}")
    private String subscribeConfigContent;
    @Value("${wechat.sdk.onsubscribe.class}")
    private String subscribeConfigClass;

    private Class<? extends WeChatMessage> realClass;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserComponent userComponent;

    @Override
    public Class<SubscribeEvent> support() {
        return SubscribeEvent.class;
    }

    @Override
    public WeChatMessage handle(SubscribeEvent message) {
        String openId = message.getFromUserName();
        UserInfo userInfo = userComponent.getUserInfo(openId, null);
        userInfo.setId(UUIDUtil.randomUUID());
        userRepository.save(userInfo);

        try {
            String json = FileUtil.readString(subscribeConfigContent);
            Map<String, Object> beanMap = BeanUtil.beanToMap(message);
            json = FileUtil.replace(json, beanMap);
            WeChatMessage result = JsonUtil.fromJson(json, realClass);
            return result;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        realClass = (Class<? extends WeChatMessage>) Class.forName(subscribeConfigClass);
    }
}
