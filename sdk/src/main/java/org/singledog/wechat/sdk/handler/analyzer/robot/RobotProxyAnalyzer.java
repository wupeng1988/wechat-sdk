package org.singledog.wechat.sdk.handler.analyzer.robot;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.singledog.wechat.sdk.handler.analyzer.MessageAnalyzer;
import org.singledog.wechat.sdk.holder.ThreadLocalHolder;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.message.reply.ReplyStringMessage;
import org.singledog.wechat.sdk.message.reply.ReplyTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
public abstract class RobotProxyAnalyzer<T extends WeChatMessage> implements MessageAnalyzer<T> {
    private static final Logger logger = LoggerFactory.getLogger(RobotProxyAnalyzer.class);

    private final String charset = "utf-8";

    @Value("${wechat.sdk.robot.accessUrl}")
    protected String accessUrl;

    private static final CloseableHttpClient client = HttpClients.createDefault();

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

//    @Override
//    public WeChatMessage analyze(T weChatMessage) {
//        String originalXml = ThreadLocalHolder.getOriginalXml();
//        logger.debug("received message : {}", originalXml);
//
//        String response = null;
//        try {
//            response = HttpUtil.post(accessUrl, originalXml, null);
//        } catch (Exception e) {
//            response = "Oops... 出错了...";
//            ReplyTextMessage textMessage = new ReplyTextMessage(weChatMessage);
//            textMessage.setContent(response);
//            logger.error(e.getMessage(), e);
//            return textMessage;
//        }
//
//        logger.debug("receive message from robot : {}", response);
//
//        return new ReplyStringMessage(weChatMessage, response);
//    }

    @Override
    public WeChatMessage analyze(T weChatMessage) {
        String originalXml = ThreadLocalHolder.getOriginalXml();
        logger.debug("received message : {}", originalXml);

        String response = null;
        try {
            HttpPost post = new HttpPost(accessUrl);
            post.setEntity(new StringEntity(originalXml, ContentType.create(ContentType.APPLICATION_XML.getMimeType(), charset)));
            CloseableHttpResponse responseEntity = null;
            HttpEntity entity = null;
            BufferedReader br = null;
            InputStream is = null;
            try {
                StringBuilder stringBuilder = new StringBuilder();
                responseEntity = client.execute(post);
                entity = responseEntity.getEntity();
                is = entity.getContent();
                br = new BufferedReader(new InputStreamReader(is, charset));
                String msg = null;
                while ((msg = br.readLine()) != null) {
                    stringBuilder.append(msg);
                }
                response = stringBuilder.toString();
            } finally {
                if (br != null)
                    br.close();
                if (is != null)
                    is.close();
                if (responseEntity != null)
                    responseEntity.close();
            }
        } catch (Exception e) {
            response = "Oops... 出错了...";
            ReplyTextMessage textMessage = new ReplyTextMessage(weChatMessage);
            textMessage.setContent(response);
            logger.error(e.getMessage(), e);
            return textMessage;
        }

        logger.debug("receive message from robot : {}", response);

        return new ReplyStringMessage(weChatMessage, response);
    }

    @Override
    public boolean support(T weChatMessage) {
        return true;
    }

}
