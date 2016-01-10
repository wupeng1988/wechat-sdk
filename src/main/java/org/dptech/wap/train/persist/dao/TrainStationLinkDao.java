package org.dptech.wap.train.persist.dao;

import org.dptech.wap.train.persist.mapper.TrainStationLinkMapper;
import org.dptech.wap.train.persist.model.TrainStationLink;
import org.dptech.wap.train.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class TrainStationLinkDao {

	@Autowired
	private TrainStationLinkMapper mapper;
	
	public int insert(TrainStationLink info){
		if(StringUtils.isEmpty(info.getId())){
			info.setId(UuidUtil.randomUUID());
		}
		
		return mapper.insert(info);
	}
	
	public int update(TrainStationLink link){
		return mapper.updateByPrimaryKey(link);
	}
	
	public int updateSelective(TrainStationLink link){
		return mapper.updateByPrimaryKeySelective(link);
	}
	
	public TrainStationLink getByTrainNoAndStationCode(String trainNo, String stationCode){
		return mapper.getByTrainNoAndStationCode(trainNo, stationCode);
	}
	
}
