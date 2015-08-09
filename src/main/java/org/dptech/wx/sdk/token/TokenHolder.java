package org.dptech.wx.sdk.token;


/**
 * 单机模式下使用此类
 * 
 * @author wupeng
 * 
 */
public class TokenHolder {

	private static AccessToken token;

	private static long last_refresh = 0;

	/**
	 * 获取token
	 * 
	 * @return
	 */
	public static String getToken() {
		if (shuoldRefresh()) {// 需要重新刷新token
			token = AccessTokenAPI.getInstance().refreshToken();
			last_refresh = System.currentTimeMillis();
		}

		return token.getAccess_token();
	}
	
	public static boolean refreshToken(){
		token = null;
		getToken();
		return true;
	}

	/**
	 * 是否需要刷新
	 * 
	 * @return
	 */
	private static boolean shuoldRefresh() {
		if (token == null) {
			return true;
		}

		long period = System.currentTimeMillis() - last_refresh;
		if (period >= token.getRefresh_period()) {
			return true;
		}

		return false;
	}

}