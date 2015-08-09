package org.dptech.wap.train.persist.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 车次信息
 * 
 * @author wupeng
 *
 */
public class TrainInfo extends TimeManaged {

	String id;
	/**
	 * 车次
	 */
	String trainNo;
	/**
	 * 类型
	 */
	String type;
	/**
	 * 始发站
	 */
	String startStationCode;
	/**
	 * 终点站
	 */
	String endStationCode;
	/**
	 * 开车时间
	 */
	String startTime;
	/**
	 * 到站时间
	 */
	String endTime;
	/**
	 * 总里程
	 */
	String totalDistance;
	/**
	 * 总时间
	 */
	String totalTime;
	/**
	 * 摘要 eg.包头-北京(当天到)
	 */
	String summery;

	List<TrainPriceInfo> prices = new ArrayList<TrainPriceInfo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartStationCode() {
		return startStationCode;
	}

	public void setStartStationCode(String startStationCode) {
		this.startStationCode = startStationCode;
	}

	public String getEndStationCode() {
		return endStationCode;
	}

	public void setEndStationCode(String endStationCode) {
		this.endStationCode = endStationCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getSummery() {
		return summery;
	}

	public void setSummery(String summery) {
		this.summery = summery;
	}

	public List<TrainPriceInfo> getPrices() {
		return prices;
	}

	public void setPrices(List<TrainPriceInfo> prices) {
		this.prices = prices;
	}

	public void addPrice(TrainPriceInfo price) {
		this.prices.add(price);
	}

}
