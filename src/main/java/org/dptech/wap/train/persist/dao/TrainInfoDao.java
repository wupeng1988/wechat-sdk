package org.dptech.wap.train.persist.dao;

import org.dptech.wap.train.persist.mapper.TrainInfoMapper;
import org.dptech.wap.train.persist.model.TrainInfo;
import org.dptech.wap.train.util.UuidUtil;
import org.dptech.wx.sdk.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class TrainInfoDao {

	@Autowired
	private TrainInfoMapper trainInfoMapper;

	public int insert(TrainInfo trainInfo) {
		if (StringUtils.isEmpty(trainInfo.getId())) {
			trainInfo.setId(UuidUtil.randomUUID());
		}
		
		return trainInfoMapper.insert(trainInfo);
	}
	
	/**
	 * 
	 * 根据车次查找车次信息
	 * 
	 * @param trainNo
	 * @return
	 */
	public TrainInfo getByTrainNo(String trainNo){
		if(StringUtil.isEmpty(trainNo))
			return null;
		
		return this.trainInfoMapper.getByTrainNo(trainNo);
	}

	public int update(TrainInfo trainInfo){
		return this.trainInfoMapper.updateByPrimaryKey(trainInfo);
	}
	
	public int updateSelective(TrainInfo trainInfo){
		return this.trainInfoMapper.updateByPrimaryKeySelective(trainInfo);
	}
}
