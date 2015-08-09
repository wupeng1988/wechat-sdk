package org.dptech.wap.train.spider;

import java.util.List;

import org.dptech.wap.train.service.TrainInfoService;
import org.dptech.wap.train.service.TrainStationLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TrainInfoSpiderDispatcher {

	private static final Logger logger = LoggerFactory.getLogger(TrainInfoSpiderDispatcher.class);
	
	private List<TrainInfoSpider> trainInfoSpiders;
	
	@Autowired
	private TrainInfoService trainInfoService;
	
	@Autowired
	private TrainStationLinkService trainStationLinkService; 
	
	@Autowired
	public TrainInfoSpiderDispatcher(List<TrainInfoSpider> trainInfoSpiders){
		this.trainInfoSpiders = trainInfoSpiders;
	}
	
	/**
	 * 
	 * 找到对应的spider，抽取车次信息
	 * 
	 * @param trainNo
	 * @return
	 */
	public CompositeTrainStationInfo extractTrainStationInfo(String trainNo){
		CompositeTrainStationInfo c = null; 
		try {
			for(TrainInfoSpider spider : trainInfoSpiders){
				if(spider.support(trainNo)){
					logger.debug(spider.getClass().getName() + " handle start grab ...");
					c = spider.extract(trainNo);
				}
			}
		} catch (ExtractException e){
			logger.error(e.getMessage(), e);
		}
		
		return c;
	}
	
	/**
	 * 
	 * 持久化 车次信息
	 * 
	 * @param trainStationInfo
	 */
	@Transactional
	public void persistCompositeTrainStationInfo(CompositeTrainStationInfo trainStationInfo){
		logger.debug("start merge train station info !");
		if(trainStationInfo == null){
			return;
		}
		
		if(trainStationInfo.getTrainInfo() != null){
			this.trainInfoService.mergeTrainInfo(trainStationInfo.getTrainInfo());
		} 
		
		if(trainStationInfo.getTrainStationLink() != null){
			this.trainStationLinkService.mergeTrainStationLink(trainStationInfo.getTrainStationLink());
		}
		logger.debug("merge train station info complete !");
	}
	
}
