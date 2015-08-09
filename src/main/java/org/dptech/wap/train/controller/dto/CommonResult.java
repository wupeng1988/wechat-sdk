package org.dptech.wap.train.controller.dto;

public class CommonResult<T> {

	private int code;

	private String msg;

	private T data;

	public CommonResult(){}
	
	public CommonResult(int code){
		this.code = code;
	}
	
	public CommonResult(int code, String msg){
		this.code = code;
		this.msg = msg;
	}	
	
	public CommonResult(int code, String message, T data){
		this.code = code;
		this.msg = message;
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
