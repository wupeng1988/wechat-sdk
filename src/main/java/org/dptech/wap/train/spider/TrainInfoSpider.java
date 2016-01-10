package org.dptech.wap.train.spider;

/**
 * 
 * 抓取火车信息
 * 
 * @author wupeng
 *
 */
public interface TrainInfoSpider {
	
	/**
	 * 
	 * 是否支持
	 * 
	 * @param trainNo 火车车次
	 * @return
	 */
	public boolean support(String trainNo);
	
	/**
	 * 
	 * 抓取车次时刻表信息
	 * 
	 * @param trainNo 车次
	 * @return
	 */
	public CompositeTrainStationInfo extract(String trainNo) throws ExtractException;
	
}
