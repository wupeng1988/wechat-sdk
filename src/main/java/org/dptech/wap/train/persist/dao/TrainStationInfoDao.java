package org.dptech.wap.train.persist.dao;

import org.dptech.wap.train.persist.mapper.TrainStationInfoMapper;
import org.dptech.wap.train.persist.model.TrainStationInfo;
import org.dptech.wap.train.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class TrainStationInfoDao {

	@Autowired
	TrainStationInfoMapper mapper;
	
	public int insert(TrainStationInfo info){
		if(StringUtils.isEmpty(info.getId())){
			info.setId(UuidUtil.randomUUID());
		}
		
		return mapper.insert(info);
	}
	
	public TrainStationInfo getTrainStationInfoByName(String name){
		if(StringUtils.isEmpty(name))
			return null;
		
		return mapper.getByName(name);
	}
	
}
