package org.singledog.wechat.sdk.exception;

public class TokenExpireException extends RuntimeException{

	private static final long serialVersionUID = 4344669627738508089L;

	public TokenExpireException(String msg){
		super(msg);
	}
	
	public TokenExpireException(String msg, Throwable th){
		super(msg, th);
	}
	
}