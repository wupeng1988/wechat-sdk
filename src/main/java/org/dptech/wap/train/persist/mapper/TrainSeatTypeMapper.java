package org.dptech.wap.train.persist.mapper;

import org.dptech.wap.train.persist.model.TrainSeatType;

public interface TrainSeatTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(TrainSeatType record);

    int insertSelective(TrainSeatType record);

    TrainSeatType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TrainSeatType record);

    int updateByPrimaryKey(TrainSeatType record);
}