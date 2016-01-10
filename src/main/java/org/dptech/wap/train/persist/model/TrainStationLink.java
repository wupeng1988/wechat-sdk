package org.dptech.wap.train.persist.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 列车经过的车站
 * 
 * @author wupeng
 *
 */
public class TrainStationLink extends TimeManaged{

	String id;
	/**
	 * 车次
	 */
	String trainNo;
	/**
	 * 站次
	 */
	int stationOrder;
	/**
	 * 车站编号
	 */
	String stationCode;
	/**
	 * 车站名称 - 冗余字段
	 */
	String stationName;
	/**
	 * 到达时间
	 */
	String arriveTime;
	/**
	 * 离开时间
	 */
	String leaveTime;
	/**
	 * 停车时间
	 */
	Double stayTime;
	/**
	 * 运行时间
	 */
	String runTime;
	/**
	 * 天数
	 */
	String days;
	/**
	 * 运行距离
	 */
	Double distance;

	List<TrainStationPriceInfo> prices = new ArrayList<>();

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

	public int getStationOrder() {
		return stationOrder;
	}

	public void setStationOrder(int stationOrder) {
		this.stationOrder = stationOrder;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Double getStayTime() {
		return stayTime;
	}

	public void setStayTime(Double stayTime) {
		this.stayTime = stayTime;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<TrainStationPriceInfo> getPrices() {
		return prices;
	}

	public void setPrices(List<TrainStationPriceInfo> prices) {
		this.prices = prices;
	}

	public void addPrice(TrainStationPriceInfo priceInfo) {
		this.prices.add(priceInfo);
	}

}
