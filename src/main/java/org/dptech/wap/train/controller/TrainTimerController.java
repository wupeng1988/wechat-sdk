package org.dptech.wap.train.controller;

import org.dptech.wap.train.controller.dto.CommonResult;
import org.dptech.wap.train.controller.dto.ReturnCode;
import org.dptech.wap.train.spider.CompositeTrainStationInfo;
import org.dptech.wap.train.spider.TrainInfoSpiderDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/train/schedule")
public class TrainTimerController {
	
	@Autowired
	private TrainInfoSpiderDispatcher dispatcher;
	
//	private 

	@RequestMapping("/bytrain/{trainNo}")
	public Object findStations(@PathVariable("trainNo") String trainNo){
		
		CompositeTrainStationInfo trainInfo = dispatcher.extractTrainStationInfo(trainNo);
		dispatcher.persistCompositeTrainStationInfo(trainInfo);
		
		return new CommonResult(ReturnCode.SUCCESS, "success");
	}
	
}
