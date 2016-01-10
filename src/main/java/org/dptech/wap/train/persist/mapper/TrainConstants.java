package org.dptech.wap.train.persist.mapper;

public interface TrainConstants {
	/**
	 * 车站信息是否可用 可用
	 */
	byte TRAIN_STATION_VALID_Y = 1;
	/**
	 * 车站信息是否可用 不可用
	 */
	byte TRAIN_STATION_VALID_N = 1;
	
	/**
	 * 硬卧上
	 */
	String SEAT_TYPE_YINGWO_SHANG = "yingwoshang";
	/**
	 * 硬卧下
	 */
	String SEAT_TYPE_YINGWO_XIA = "yingwoxia";
	/**
	 * 硬卧中
	 */
	String SEAT_TYPE_YINGWO_ZHONG = "yingwozhong";
	
	/**
	 * 软卧下
	 */
	String SEAT_TYPE_RUANWO_XIA = "ruanwoxia";
	/**
	 * 软卧上
	 */
	String SEAT_TYPE_RUANWO_SHANG = "ruanwoshang";
	/**
	 * 豪华软卧
	 */
	String SEAT_TYPE_HAOHUARUANWO = "haohuaruanwo";
	
	/**
	 * 硬座
	 */
	String SEAT_TYPE_YINGZUO = "yingzuo";
	/**
	 * 软座
	 */
	String SEAT_TYPE_RUANZUO = "ruanzuo";
	
	/**
	 * 二等座
	 */
	String SEAT_TYPE_ERDENGZUO = "erdengzuo";
	/**
	 * 一等座
	 */
	String SEAT_TYPE_YIDENGZUO = "yidengzuo";
	/**
	 * 商务座
	 */
	String SEAT_TYPE_SHANGWUZUO = "shangwuzuo";
	
	/**
	 * 特等座
	 */
	String SEAT_TYPE_TEDENG = "tedengzuo";
	
	
}
