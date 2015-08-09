package org.dptech.wap.train.persist.model;


/**
 * 
 * 列车票价信息
 * 
 * @author wupeng
 *
 */
public class TrainPriceInfo extends TimeManaged {

	String id;
	/**
	 * 车次
	 */
	String trainNo;
	/**
	 * 席别
	 */
	String seatTypeCode;
	/**
	 * 价格
	 */
	Double price;

	public TrainPriceInfo() {
	}

	public TrainPriceInfo(String seatType, Double price) {
		this.seatTypeCode = seatType;
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
