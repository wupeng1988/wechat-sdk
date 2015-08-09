package org.dptech.wap.train.persist.dao;

import org.dptech.wap.train.persist.mapper.TrainPriceInfoMapper;
import org.dptech.wap.train.persist.model.TrainPriceInfo;
import org.dptech.wap.train.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class TrainPriceInfoDao {

	@Autowired
	private TrainPriceInfoMapper mapper;
	
	public int insert(TrainPriceInfo info){
		if(StringUtils.isEmpty(info.getId())){
			info.setId(UuidUtil.randomUUID());
		}
		
		return mapper.insert(info);
	}
	
	public int update(TrainPriceInfo info){
		return this.mapper.updateByPrimaryKey(info);
	}
	
	public TrainPriceInfo getPriceInfo(String trainNo, String seatType){
		return mapper.getPriceInfoByTrainNoAndSeatType(trainNo, seatType);
	}
	
}
