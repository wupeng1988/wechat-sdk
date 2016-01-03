package org.singledog.wechat.sdk.handler;

import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.UnSubscribeEvent;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.user.UserInfo;
import org.singledog.wechat.sdk.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by adam on 1/3/16.
 */
@Component
public class UnSubscribeHandler implements MessageHandler<UnSubscribeEvent> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Class<UnSubscribeEvent> support() {
        return UnSubscribeEvent.class;
    }

    @Override
    public WeChatMessage handle(UnSubscribeEvent message) {
        String openId = message.getFromUserName();
        Criteria criteria = Criteria.where("openid").is(openId);
        criteria.and("subscribe").is(UserInfo.SUBSCRIBE_Y);
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC, "subscribe_time"));
        query.limit(1);

        UserInfo userInfo = this.mongoTemplate.findOne(query, UserInfo.class);
        if (userInfo != null) {
            userInfo.setId(UUIDUtil.randomUUID());
            userInfo.setSubscribe(UserInfo.SUBSCRIBE_N);
            this.mongoTemplate.save(userInfo);
        }

        return null;
    }
}
