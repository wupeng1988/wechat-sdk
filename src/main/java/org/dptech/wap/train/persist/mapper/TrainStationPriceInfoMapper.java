package org.dptech.wap.train.persist.mapper;

import org.apache.ibatis.annotations.Param;
import org.dptech.wap.train.persist.model.TrainStationPriceInfo;

public interface TrainStationPriceInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(TrainStationPriceInfo record);

    int insertSelective(TrainStationPriceInfo record);

    TrainStationPriceInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TrainStationPriceInfo record);

    int updateByPrimaryKey(TrainStationPriceInfo record);

	TrainStationPriceInfo getStationPriceInfo(@Param("trainNo")String trainNo, @Param("stationCode")String stationCode, @Param("seatType")String seatType);
}