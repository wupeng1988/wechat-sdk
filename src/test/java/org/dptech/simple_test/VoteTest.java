package org.dptech.simple_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.dptech.wx.sdk.util.HttpUtil;
import org.dptech.wx.sdk.util.JsonUtil;
import org.springframework.util.StringUtils;

public class VoteTest {
	
	
	public static void main(String[] args) throws Exception {
		
		new VoteTest().testVote();
		
	}
	
	public void testVote() throws Exception{
		String url = "http://temp.ku.com.cn/ajaxpro/Unibon.Web.Phone.Vote.Default,Web.ashx";
		Map<String, Object> map = new HashMap<>();
		map.put("openId","o-01iuKoLtNQZSoix8nQ8VyRNrko1");
		map.put("voteId","1");
		map.put("itemId",1368);
		
		String json = JsonUtil.toJson(map);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "text/xml, text/html, application/xhtml+xml, image/png, text/plain, */*;q=0.8");
		headers.put("Accept-Charset", "utf-8, iso-8859-1, utf-16, *;q=0.7");
		headers.put("Accept-Encoding", "gzip");
		headers.put("Accept-Language", "zh-CN");
		headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-cn; m1 note Build/KTU84P) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.4 TBS/025411 Mobile Safari/533.1 MicroMessenger/6.1.0.73_r1097298.543 NetType/WIFI");
		headers.put("Cookie", "ASP.NET_SessionId=euml0kfkm21khn0liqvzzids");
		headers.put("Content-Type", "text/plain; charset=UTF-8");
		headers.put("Origin", "http://temp.ku.com.cn");
		headers.put("Referer", "http://temp.ku.com.cn/phone/vote/item.aspx?openid=o-01iuKoLtNQZSoix8nQ8VyRNrko&id=1004");
		headers.put("X-AjaxPro-Method", "DoLaud");
		headers.put("Host", "temp.ku.com.cn");
		
		String response = postJson(url, json, "UTF-8", headers);
		
		System.out.println(response);
		
	}
	
	
	public String postJson(String url, String json, String encoding, Map<String, String> headers) throws IOException{
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setReadTimeout(30000);
		commonHeaders(conn, headers);
		
		if(!StringUtils.isEmpty(json)){
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes(encoding));
			os.close();
		}
		
		conn.connect();
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			String msg = null;
			while((msg = br.readLine()) != null){
				sb.append(msg).append("\n");
			}
			
			if(sb.length() > 0){
				sb.deleteCharAt(sb.length() - 1);
			}
		} finally {
			if(br != null)
				br.close();
		}
		
		return sb.toString();
	}
	
	private void commonHeaders(HttpURLConnection conn, Map<String, String> headers) {
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.setRequestProperty("user-agent", 
        		"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
        		+ "Chrome/41.0.2272.76 Safari/537.36");
        
        for(Map.Entry<String, String> entry : headers.entrySet()){
        	conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
	}

}
