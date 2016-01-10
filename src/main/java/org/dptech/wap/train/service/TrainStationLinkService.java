package org.dptech.wap.train.service;

import java.util.List;

import org.dptech.wap.train.persist.dao.TrainStationLinkDao;
import org.dptech.wap.train.persist.dao.TrainStationPriceInfoDao;
import org.dptech.wap.train.persist.model.TrainStationLink;
import org.dptech.wap.train.persist.model.TrainStationPriceInfo;
import org.dptech.wx.sdk.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainStationLinkService {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainStationLinkService.class);
	
	@Autowired
	private TrainStationLinkDao stationLinkDao;
	
	@Autowired
	private TrainStationPriceInfoDao stationPriceDao;
	
	public void mergeTrainStationLink(List<TrainStationLink> trainStationLink){
		if(trainStationLink == null)
			return;
		
		for(TrainStationLink link : trainStationLink){
			this.mergeOne(link);
		}
	}
	
	public void mergeOne(TrainStationLink link){
		if(link == null)
			return;
		
		TrainStationLink persistedLink = this.stationLinkDao.getByTrainNoAndStationCode(link.getTrainNo(), link.getStationCode());
		if(persistedLink == null){
			logger.debug("train station not exists ! insert ...");
			this.stationLinkDao.insert(link);

			for(TrainStationPriceInfo priceInfo : link.getPrices()){
				this.stationPriceDao.insert(priceInfo);
			}
		} else {
			logger.debug("train station already exists ! update ...");
			
			link.setId(persistedLink.getId());
			BeanUtil.copyAttrs4Update(persistedLink, link);
			this.stationLinkDao.update(link);
			
			for(TrainStationPriceInfo priceInfo : link.getPrices()){
				TrainStationPriceInfo persistedPriceInfo = this.stationPriceDao.getStationPriceInfo(link.getTrainNo(), 
						link.getStationCode(), priceInfo.getSeatTypeCode());
				
				if(persistedPriceInfo == null){
					this.stationPriceDao.insert(priceInfo);
				} else {
					if(persistedPriceInfo.getPrice() != null && priceInfo.getPrice() != null
							&& persistedPriceInfo.getPrice().doubleValue() == priceInfo.getPrice().doubleValue()){
						persistedPriceInfo.setPrice(priceInfo.getPrice());
						this.stationPriceDao.update(persistedPriceInfo);
					}
				}
			}
		}
	}

}
