package org.dptech.wap.train.spider;

import java.util.List;

import org.dptech.wap.train.persist.model.TrainInfo;
import org.dptech.wap.train.persist.model.TrainStationLink;

public class CompositeTrainStationInfo {

	/**
	 * 车次信息
	 */
	private TrainInfo trainInfo;

	/**
	 * 车站信息
	 */
	private List<TrainStationLink> trainStationLink;

	public TrainInfo getTrainInfo() {
		return trainInfo;
	}

	public void setTrainInfo(TrainInfo trainInfo) {
		this.trainInfo = trainInfo;
	}

	public List<TrainStationLink> getTrainStationLink() {
		return trainStationLink;
	}

	public void setTrainStationLink(List<TrainStationLink> trainStationLink) {
		this.trainStationLink = trainStationLink;
	}

}
