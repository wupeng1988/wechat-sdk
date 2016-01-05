package org.singledog.wechat.sdk.message.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.singledog.wechat.sdk.Interfaces;
import org.singledog.wechat.sdk.exception.ErrorCheckUtil;
import org.singledog.wechat.sdk.token.AccessTokenComponent;
import org.singledog.wechat.sdk.util.HttpUtil;
import org.singledog.wechat.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageComponent.class);

    private AccessTokenComponent tokenComponent;

	/**
	 * 发送客服消息  -  文本消息
	 */
	public void sendCustomTextMessage(String toUserOpenId, String content){
		try {
            String token = tokenComponent.refreshToken().getAccess_token();
            String url = Interfaces.custom_send.toString() + "?access_token=" + token;
            String json = HttpUtil.post(url, this.customTextMessage(toUserOpenId, content));
			ErrorCheckUtil.check(json);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

    public String customTextMessage(String toUserName, String content){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("touser", toUserName);
        map.put("msgtype", "text");

        Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("content", content);
        map.put("text", contentMap);
        try {
            return JsonUtil.toJson(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}