package org.singledog.wechat.sdk.user;

import org.singledog.wechat.sdk.Interfaces;
import org.singledog.wechat.sdk.exception.ErrorCheckUtil;
import org.singledog.wechat.sdk.token.AccessToken;
import org.singledog.wechat.sdk.token.AccessTokenComponent;
import org.singledog.wechat.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class UserComponent {

    private static final Logger logger = LoggerFactory.getLogger(UserComponent.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AccessTokenComponent tokenComponent;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * @param openId
     * @param lang   国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return
     * @throws IOException
     */
    public UserInfo getUserInfo(String openId, String lang) {
        AccessToken accessToken = this.tokenComponent.refreshToken();
        String access_token = accessToken.getAccess_token();
        StringBuffer url = new StringBuffer(Interfaces.user_info.toString())
                .append("?access_token=").append(access_token).append("&openid=").append(openId);
        if (lang != null && !"".equals(lang)) {
            url.append("&lang=").append(lang);
        }

        String json = restTemplate.getForObject(url.toString(), String.class);
        logger.debug("get userInfo, server return : {} for url : {}", json, url.toString());
        ErrorCheckUtil.check(json);
        UserInfo info = JsonUtil.fromJson(json, UserInfo.class);
        return info;
    }


    public UserInfo getUserInfoFromDB(String openId) {
        Criteria criteria = Criteria.where("openid").is(openId);
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC, "subscribe_time"));
        query.limit(1);

        return this.mongoTemplate.findOne(query, UserInfo.class);
    }

}