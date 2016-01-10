package org.dptech.wap.train.persist.mapper;

import org.apache.ibatis.annotations.Param;
import org.dptech.wap.train.persist.model.TrainStationLink;

public interface TrainStationLinkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TrainStationLink record);

    int insertSelective(TrainStationLink record);

    TrainStationLink selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TrainStationLink record);

    int updateByPrimaryKey(TrainStationLink record);

	TrainStationLink getByTrainNoAndStationCode(@Param("trainNo")String trainNo, @Param("stationCode")String stationCode);
}