package org.dptech.wap.train.persist.mapper;

import org.apache.ibatis.annotations.Param;
import org.dptech.wap.train.persist.model.TrainPriceInfo;

public interface TrainPriceInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(TrainPriceInfo record);

    int insertSelective(TrainPriceInfo record);

    TrainPriceInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TrainPriceInfo record);

    int updateByPrimaryKey(TrainPriceInfo record);

	TrainPriceInfo getPriceInfoByTrainNoAndSeatType(@Param("trainNo")String trainNo, @Param("seatType")String seatType);
}