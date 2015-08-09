package org.dptech.wap.train.persist.dao;

import org.dptech.wap.train.persist.mapper.TrainStationPriceInfoMapper;
import org.dptech.wap.train.persist.model.TrainStationPriceInfo;
import org.dptech.wap.train.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class TrainStationPriceInfoDao {
	
	@Autowired
	private TrainStationPriceInfoMapper mapper;
	
	public int insert(TrainStationPriceInfo info){
		if(StringUtils.isEmpty(info.getId())){
			info.setId(UuidUtil.randomUUID());
		}
		
		return mapper.insert(info);
	}
	
	public int update(TrainStationPriceInfo priceInfo){
		return this.mapper.updateByPrimaryKey(priceInfo);
	}
	
	public int updateSelective(TrainStationPriceInfo priceInfo){
		return this.mapper.updateByPrimaryKeySelective(priceInfo);
	}
	
	public TrainStationPriceInfo getStationPriceInfo(String trainNo, String stationCode, String seatType){
		return mapper.getStationPriceInfo(trainNo, stationCode, seatType);
	}

}
