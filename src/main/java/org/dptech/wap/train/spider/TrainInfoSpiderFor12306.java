package org.dptech.wap.train.spider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dptech.wap.train.util.DateUtil;
import org.dptech.wx.sdk.util.HttpUtil;
import org.dptech.wx.sdk.util.HttpsUtil;
import org.dptech.wx.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TrainInfoSpiderFor12306 implements TrainInfoSpider{
	private static final Logger logger = LoggerFactory.getLogger(TrainInfoSpiderFor12306.class);
	
	private static final Map<String, String> headers = new HashMap<>();
	
	static {
		headers.put("Accept", "text/javascript, text/html, application/xml, text/xml, */*");
		headers.put("Accept-Encoding", "gzip,deflate");
		headers.put("Accept-Language", "zh_CN");
		headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 4.4.4; m1 note Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36/Worklight/6.0.0");
		headers.put("X-Requested-With", "XMLHttpRequest");
		headers.put("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Origin", "file://");
		headers.put("WL-Instance-Id", "mbqpvooei3mh4273uv4lvapfta");
		headers.put("x-wap-profile", "http://218.249.47.94/Xianghe/MTK_LTE_Phone_KK_UAprofile.xml");
		headers.put("x-wl-app-version", "2.0");
		headers.put("x-wl-platform-version", "6.0.0");
		headers.put("Cookie", "JSESSIONID=0000uCZHAHoQ74Grh8Jy92PWr3G:18apo671m; BIGipServerworklight=4023845130.16420.0000; BIGipServernginxformobile=3269001482.50215.0000");
	}

	@Override
	public boolean support(String trainNo) {
		return false;
	}

	@Override
	public CompositeTrainStationInfo extract(String trainNo) throws ExtractException {
		if(StringUtils.isEmpty(trainNo)){
			throw new ExtractException("trainNo is empty !");
		}
		
		trainNo = trainNo.toUpperCase();
		
		String url = "https://mobile.12306.cn/otsmobile/apps/services/api/MobileTicket/android/query";
		
		try {
			String params = this.params(trainNo);
//			String json = HttpsUtil.post(url, params, HttpsUtil.default_charset, headers);
			String json = HttpsUtil.post(url, params, "Unicode", headers);
			System.out.println(json);
			
		} catch (JsonProcessingException e) {
			logger.error("convert params error ! "+e.getMessage(), e);
			throw new ExtractException("convert params error !", e);
		} catch (IOException e) {
			logger.error("send request error ! " + e.getMessage(), e);
			throw new ExtractException("send request error !", e);
		} catch (KeyManagementException | NoSuchAlgorithmException | NoSuchProviderException e) {
			logger.error("ssl error ! " + e.getMessage(), e);
			throw new ExtractException("ssl error ! ", e);
		}
		
		return null;
	}
	
	public static void main(String[] args) throws ExtractException {
		new TrainInfoSpiderFor12306().extract("K52");
	}
	
	private String params(String trainNo) throws JsonProcessingException, UnsupportedEncodingException{
		Map<String, Object> params = new HashMap<>();
		params.put("adapter", "CARSMobileServiceAdapterV2");
		params.put("procedure", "queryTrainInfo");
		params.put("compressResponse", "true");
		params.put("__wl_deviceCtxVersion", "-1");
		params.put("__wl_deviceCtxSession", "161727491427506622657");
		params.put("isAjaxRequest", "true");
		params.put("x", Math.random());
		
		Map<String, Object> subParams = new HashMap<>();
		subParams.put("baseDTO.os_type", "a");
		subParams.put("baseDTO.device_no", "6138dffb29742406");
		subParams.put("baseDTO.mobile_no", "123444");
		Date date = new Date();
		subParams.put("baseDTO.time_str", DateUtil.format("yyyyMMddHHmmss", date));
		subParams.put("baseDTO.check_code", "8cdd86a86b8bf09f0e1b2d5fe76a76dc");
		subParams.put("baseDTO.version_no", "1.1");
		subParams.put("baseDTO.user_name", "wp19880404");
		subParams.put("start_train_date", DateUtil.format("yyyyMMdd", date));
		subParams.put("train_code", trainNo);
		List<Map<String, Object>> lists = new ArrayList<>();
		lists.add(subParams);
		
		params.put("parameters", URLEncoder.encode(JsonUtil.toJson(lists), "UTF-8"));
		
		return HttpUtil.toParams(params);
	} 

}
