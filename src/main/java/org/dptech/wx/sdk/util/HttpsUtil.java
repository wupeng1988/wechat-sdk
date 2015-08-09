package org.dptech.wx.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class HttpsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpsUtil.class);
	
	public static String default_charset = "UTF-8";
	
	public static String post(String url, String param) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
		return post(url, param, default_charset, null);
	}
	
	public static String post(String url, String param, String encoding, Map<String, String> headers) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
		URL uri = new URL(url);
//		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("localhost", 8888));
		
		HttpsURLConnection conn = (HttpsURLConnection) uri.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setReadTimeout(30000);
		security(conn);
		commonHeaders(conn, headers);
		
		if(!StringUtils.isEmpty(param)){
			OutputStream os = conn.getOutputStream();
			os.write(param.getBytes(encoding));
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
	
	private static void commonHeaders(HttpsURLConnection conn, Map<String, String> headers) {
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.setRequestProperty("user-agent", 
        		"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
        		+ "Chrome/41.0.2272.76 Safari/537.36");
        
        if(headers != null){
        	for(Map.Entry<String, String> entry : headers.entrySet()){
        		conn.setRequestProperty(entry.getKey(), entry.getValue());
        	}
        }
	}
	
	
	private static void security(HttpsURLConnection conn) throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException{
		
		//创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = {new SimpleX509TrustManagerImpl()}; 
		SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE"); 
		sslContext.init(null, tm, new java.security.SecureRandom()); 

		//从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		//创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
		conn.setSSLSocketFactory(ssf);
		
		conn.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}

}
