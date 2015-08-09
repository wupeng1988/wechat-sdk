package org.dptech.wx.sdk.token;

import java.util.concurrent.TimeUnit;

public class AccessToken {

	private String access_token;
	private int expires_in;//秒
	private long refresh_period;//刷新时间段，毫秒
	
	public String getAccess_token() {
		return access_token;
	}

	void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
		this.refresh_period = TimeUnit.SECONDS.toMillis(expires_in - 600);//刷新时间比过期时间早十分钟
	}

	public long getRefresh_period() {
		return refresh_period;
	}

}