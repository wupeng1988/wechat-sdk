package org.dptech.wx.sdk.common;

import org.dptech.wx.sdk.exception.TokenExpireException;

public interface AccessTokenCallback<T> {
	
	public T withAccessToken(String access_token) throws TokenExpireException;

}