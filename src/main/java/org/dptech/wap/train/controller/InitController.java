package org.dptech.wap.train.controller;

import javax.servlet.http.HttpServletRequest;

import org.dptech.wap.train.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/init")
public class InitController {

	@Autowired
	InitService initService;
	
	@RequestMapping("/hello")
	public Object hello(HttpServletRequest request){
		String msg = initService.init();
		String ip = request.getRemoteAddr() + ":" + request.getRemotePort() + ":" + msg;
		return ip;
	}
	
}
