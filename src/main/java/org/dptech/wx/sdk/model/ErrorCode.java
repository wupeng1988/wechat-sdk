package org.dptech.wx.sdk.model;

/**
 * 
 * 请求微信接口时返回错误信息
 * 
 * @author wupeng
 * 
 */
public class ErrorCode {

	private String errcode;
	private String errmsg;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}