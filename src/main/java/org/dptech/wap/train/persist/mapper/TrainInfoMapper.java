package org.dptech.wap.train.persist.mapper;

import org.dptech.wap.train.persist.model.TrainInfo;

public interface TrainInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(TrainInfo record);

    int insertSelective(TrainInfo record);

    TrainInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TrainInfo record);

    int updateByPrimaryKey(TrainInfo record);

	TrainInfo getByTrainNo(String trainNo);
}