package org.singledog.wechat.sdk.controller;

import org.singledog.wechat.sdk.conf.WechatConfig;
import org.singledog.wechat.sdk.handler.HandlerDispatcher;
import org.singledog.wechat.sdk.message.MessageFactory;
import org.singledog.wechat.sdk.message.MessageHandler;
import org.singledog.wechat.sdk.message.WeChatMessage;
import org.singledog.wechat.sdk.util.CommonEncryptUtil;
import org.singledog.wechat.sdk.util.XmlUtil2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("${wechat.sdk.controller.path:/wechat}")
public class WechatController {

	private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

	@Autowired
	private WechatConfig config;
    @Autowired
    private HandlerDispatcher dispatcher;
    @Autowired
    private MessageFactory messageFactory;


	/**
	 * 互动
	 * 
	 * @param request
	 * @param response
	 * @param xml
	 * @return
	 */
	@RequestMapping(value="/interaction", produces = {"text/html;charset=UTF-8"})
	public @ResponseBody String callback(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody(required=true) String xml){
		response.setCharacterEncoding("utf-8");

		Map<String, String[]> map = request.getParameterMap();
		if(map.containsKey("signature") && map.containsKey("timestamp") && map.containsKey("nonce") && map.containsKey("echostr")){
            logger.debug("check auth ...");
			return this.tokenAuth(request, response);
		}
		
		if(xml == null || "".equals(xml)){
			return "";
		}

        logger.debug("recieved xml : {}", xml);
        String msg = "";
        Map<String, String> xmlMap = XmlUtil2.toMap(xml);
        WeChatMessage message = messageFactory.getMessage(xmlMap);
        if (message != null) {
            MessageHandler messageHandler = dispatcher.getMessageHandler(message.getClass());
            if (messageHandler != null) {
                WeChatMessage result = messageHandler.handle(message);
                if (request != null)
                    msg = XmlUtil2.beanToXml(result);
            }
        }

		return msg;
	}
	
	
	
	/**
	 * 微信校验token
	 * 加密/校验流程如下：
		1. 将token、timestamp、nonce三个参数进行字典序排序
		2. 将三个参数字符串拼接成一个字符串进行sha1加密
		3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping(value="/tokenAuth", produces = {"text/html;charset=UTF-8"})
	public String tokenAuth(HttpServletRequest request, HttpServletResponse response){
		
		String token = config.getToken();
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		String msg = request.getParameter("echostr");
		
		if(StringUtils.isEmpty(signature)){
			return "fail";
		}
		
		String[] params = new String[]{token, timestamp, nonce};
		Arrays.sort(params);
		
		String auth = CommonEncryptUtil.SHA1(params[0]+params[1]+params[2]);
		if(signature.equals(auth)){
			return msg;
		}
		
		return "fail";
	}
	
	/**
	 * 获取js sdk 签名相关信息
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping(value="/jsToken", produces={"application/json;charset=UTF-8"})
	public @ResponseBody Object JSToken(HttpServletRequest request, HttpServletResponse response){
		ResponseUtil.allowCrossDomain(response);
		String key = request.getParameter("key");
		
		CommonVO vo = new CommonVO();
		if(StringUtils.isEmpty(key)){
			vo.setCode(1400);
			vo.setMsg("key can not be null");
			vo.setData("");
			return vo;
		}
		try {
			JedisCommands jedis = RedisDBUtil.getRedisTemplate();
			JSSignature signature = TokenHolder2M.getJsSignature(jedis, key);
			JSSignatureVO jsVO = new JSSignatureVO();
			jsVO.setAppId(signature.getAppId());
			jsVO.setNoncestr(signature.getNoncestr());
			jsVO.setSignature(signature.getSignature());
			jsVO.setTimestamp(signature.getTimestamp());
			RedisDBUtil.closeJedis(jedis);
			
			vo.setCode(200);
			vo.setData(jsVO);
			vo.setMsg("success!");
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return vo;
	}*/
}