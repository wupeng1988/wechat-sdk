package org.singledog.wechat.sdk.controller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.singledog.wechat.sdk.conf.WechatConfig;
import org.singledog.wechat.sdk.handler.HandlerDispatcher;
import org.singledog.wechat.sdk.holder.ThreadLocalHolder;
import org.singledog.wechat.sdk.message.MessageFactory;
import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.message.entity.MessageEntity;
import org.singledog.wechat.sdk.message.entity.MessageService;
import org.singledog.wechat.sdk.message.reply.ReplyStringMessage;
import org.singledog.wechat.sdk.message.reply.ReplyTextMessage;
import org.singledog.wechat.sdk.util.CommonEncryptUtil;
import org.singledog.wechat.sdk.util.XmlUtil2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("${wechat.sdk.controller.path:/wechat}")
public class WechatController {

    private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    static final String ok = "success";

    @Autowired
    private WechatConfig config;
    @Autowired
    private HandlerDispatcher dispatcher;
    @Autowired
    private MessageFactory messageFactory;
    @Autowired
    private MessageService messageService;

    private final String charset = "utf-8";

    @Value("${wechat.sdk.robot.accessUrl}")
    protected String accessUrl;

    private static final CloseableHttpClient client = HttpClients.createDefault();


    /**
     * 互动
     *
     * @param request
     * @param response
     * @return
     */
//    @RequestMapping(value = "/interaction")
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        if (map.containsKey("signature") && map.containsKey("timestamp") && map.containsKey("nonce") && map.containsKey("echostr")) {
            logger.debug("check auth ...");
            return this.tokenAuth(request);
        }

        try {
            String xml = HttpRequestUtil.readString(request.getInputStream());
            if (StringUtils.isEmpty(xml)) {
                return ok;
            }

            ThreadLocalHolder.setOriginalXml(xml);
            logger.debug("recieved xml : {}", xml);
            Map<String, String> xmlMap = XmlUtil2.toMap(xml);
            WeChatMessage message = messageFactory.getMessage(xmlMap);

            return this.dealWithMessage(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ok;
        }
    }


    @RequestMapping(value = "/interaction")
    public String callback2(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");

        String originalXml = ThreadLocalHolder.getOriginalXml();
        logger.debug("received message : {}", originalXml);

        try {
            HttpPost post = new HttpPost(accessUrl);
            post.setEntity(new InputStreamEntity(request.getInputStream()));
            CloseableHttpResponse responseEntity = null;

            try {
                responseEntity = client.execute(post);
                HttpEntity entity = responseEntity.getEntity();
                entity.writeTo(response.getOutputStream());
                return null;
            } finally {
                if (responseEntity != null)
                    responseEntity.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        logger.debug("receive message from robot : {}", response);
        return "";
    }




    private String dealWithMessage(WeChatMessage message) {
        String msg = ok;
        if (message != null) {
            MessageEntity messageEntity = new MessageEntity(message);
            this.messageService.saveMessage(messageEntity);

            MessageHandler messageHandler = dispatcher.getMessageHandler(message.getClass());
            if (messageHandler != null) {
                WeChatMessage result = messageHandler.handle(message);
                MessageEntity resultEntity = null;
                if (result != null) {
                    msg = result.toXml();
                    if (msg == null) {
                        msg = XmlUtil2.beanToXml(result);
                    }
                    resultEntity = new MessageEntity(result);
                } else {
                    resultEntity = new MessageEntity();
                    resultEntity.setFromUserName(messageEntity.getToUserName());
                    resultEntity.setToUserName(messageEntity.getFromUserName());
                }

                resultEntity.setReplyId(messageEntity.getId());
                this.messageService.saveMessage(resultEntity);
            }
        }

        return msg;
    }


    /**
     * 微信校验token
     * 加密/校验流程如下：
     * 1. 将token、timestamp、nonce三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     *
     * @param request
     * @return
     */
    public String tokenAuth(HttpServletRequest request) {

        String token = config.getToken();
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");
        String msg = request.getParameter("echostr");

        if (StringUtils.isEmpty(signature)) {
            return "fail";
        }

        String[] params = new String[]{token, timestamp, nonce};
        Arrays.sort(params);

        String auth = CommonEncryptUtil.SHA1(params[0] + params[1] + params[2]);
        if (signature.equals(auth)) {
            return msg;
        }

        return "fail";
    }

    public static void main(String[] args) {
        String token = "E1EC9E65F61744BAB3CC0C0BACA14EB9";
        String timestamp = System.currentTimeMillis() / 1000 + "";
        String nonce = "ASDDVFDAWER";
        String[] params = new String[]{token, timestamp, nonce};
        Arrays.sort(params);

        String auth = CommonEncryptUtil.SHA1(params[0] + params[1] + params[2]);
        System.out.println(auth);
        System.out.println(timestamp);
    }
}