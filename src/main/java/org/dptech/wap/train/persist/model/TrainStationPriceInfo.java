package org.dptech.wap.train.persist.model;


/**
 * 
 * 不同列车在不同的站的票价
 * 
 * @author wupeng
 *
 */
public class TrainStationPriceInfo extends TimeManaged {

	String id;
	/**
	 * 车次
	 */
	String trainNo;
	/**
	 * 车站编码
	 */
	String stationCode;
	/**
	 * 席别
	 */
	String seatTypeCode;
	/**
	 * 票价
	 */
	Double price;

	public TrainStationPriceInfo() {
	}

	public TrainStationPriceInfo(String trainNo, String stationCode,
			String seatTypeCode, Double price) {
		this.trainNo = trainNo;
		this.stationCode = stationCode;
		this.seatTypeCode = seatTypeCode;
		this.price = price;
	}

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

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getSeatTypeCode() {
		return seatTypeCode;
	}

	public void setSeatTypeCode(String seatTypeCode) {
		this.seatTypeCode = seatTypeCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
