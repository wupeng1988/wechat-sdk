package org.dptech.wap.train.service;

import org.dptech.wap.train.persist.dao.TrainInfoDao;
import org.dptech.wap.train.persist.dao.TrainPriceInfoDao;
import org.dptech.wap.train.persist.model.TrainInfo;
import org.dptech.wap.train.persist.model.TrainPriceInfo;
import org.dptech.wx.sdk.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainInfoService.class);
	
	@Autowired
	private TrainInfoDao trainInfoDao;
	
	@Autowired
	private TrainPriceInfoDao trainPriceInfoDao;
	
	@Transactional
	public void mergeTrainInfo(TrainInfo trainInfo){
		logger.debug("start merge train info...");
		if(trainInfo == null)
			return;
		
		String trainNo = trainInfo.getTrainNo();
		TrainInfo persisted = trainInfoDao.getByTrainNo(trainNo);
		if(persisted == null){//还没有这个车次，直接插入
			logger.debug("train info not exists , insert directly ");
			this.trainInfoDao.insert(trainInfo);
			
			for(TrainPriceInfo priceInfo : trainInfo.getPrices()){
				this.trainPriceInfoDao.insert(priceInfo);
			}
		} else {// 有了这个车次，更新
			logger.debug("train info already exists, update ...");
			trainInfo.setId(persisted.getId());
			BeanUtil.copyAttrs4Update(persisted, trainInfo);
			this.trainInfoDao.update(persisted);
			
			for(TrainPriceInfo priceInfo : trainInfo.getPrices()){
				TrainPriceInfo persistedPrice = this.trainPriceInfoDao.getPriceInfo(trainNo, priceInfo.getSeatTypeCode());
				if(persistedPrice == null){
					this.trainPriceInfoDao.insert(priceInfo);
				} else {
					if(persistedPrice.getPrice() != null && priceInfo.getPrice() != null 
							&& persistedPrice.getPrice().doubleValue() == priceInfo.getPrice().doubleValue()){
						persistedPrice.setPrice(priceInfo.getPrice());
						this.trainPriceInfoDao.update(persistedPrice);
					}
				}
			}
			
		}
		
		logger.debug("start merge train info complete !");
	}

}
