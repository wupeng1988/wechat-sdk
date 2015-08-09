package org.dptech.wap.train.persist.mapper;

import org.dptech.wap.train.persist.model.TrainStationInfo;

public interface TrainStationInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(TrainStationInfo record);

    int insertSelective(TrainStationInfo record);

    TrainStationInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TrainStationInfo record);

    int updateByPrimaryKey(TrainStationInfo record);

	TrainStationInfo getByName(String name);
}