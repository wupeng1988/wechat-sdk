package org.dptech.wap.train.persist.model;

import org.dptech.wap.train.persist.mapper.TrainConstants;

/**
 * 
 * 车站信息
 * 
 * @author wupeng
 *
 */
public class TrainStationInfo extends TimeManaged {

	String id;

	String code;

	String name;

	Byte isValid = TrainConstants.TRAIN_STATION_VALID_Y;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getIsValid() {
		return isValid;
	}

	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}

}
