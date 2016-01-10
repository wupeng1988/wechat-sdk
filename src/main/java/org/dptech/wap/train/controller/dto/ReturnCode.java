package org.dptech.wap.train.controller.dto;

/**
 * 
 * 通用返回码
 * 
 * @author Administrator
 *
 */
public interface ReturnCode {
	/**
	 * 成功
	 */
	int SUCCESS = 200;
	
	/**
	 * 参数错误
	 */
	int PARAM_ERROR = 400;
	
	/**
	 * 没有权限，访问被拒绝
	 */
	int ACCESS_DENIED = 401;
	
	/**
	 * 未登录
	 */
	int NOT_LOGIN = 402;
	
	/**
	 * 服务器内部错误
	 */
	int SERVER_INTERNAL_ERROR = 500;
	
	
	
	
	
}
