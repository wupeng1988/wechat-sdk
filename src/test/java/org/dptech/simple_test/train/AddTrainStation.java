package org.dptech.simple_test.train;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dptech.wap.train.persist.dao.TrainStationInfoDao;
import org.dptech.wap.train.persist.mapper.TrainConstants;
import org.dptech.wap.train.persist.model.TrainStationInfo;
import org.htmlparser.util.ParserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 初始化 所有车站信息
 * 
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:applicationContext.xml")
public class AddTrainStation {
	
	@Autowired
	TrainStationInfoDao stationDao;
	
	@Test
	@Rollback(false)
	public void allStations() throws IOException, ParserException{
		Map<String, String> map = new HashMap<String, String>();//code, name 
//		map.put("huian", "惠安");
//		map.put("xianyou", "仙游");
//		map.put("xianyou", "仙游");
//		map.put("malan", "马兰");
//		map.put("heshuo", "和硕");
//		map.put("shexian", "涉县");
//		map.put("xijiang", "西江");
//		map.put("muzhen", "木镇");
//		map.put("beimuzhen", "碑木镇");
//		map.put("daqingdong", "大庆东");
//		map.put("qiaowan", "桥湾");
//		map.put("liugou", "柳沟");
		map.put("jingdian", "井店");
		
		
		for(Map.Entry<String, String> entry : map.entrySet()){
			TrainStationInfo stationInfo = new TrainStationInfo();
			stationInfo.setCode(entry.getKey());
			stationInfo.setName(entry.getValue());
			
			stationInfo.setIsValid(TrainConstants.TRAIN_STATION_VALID_Y);
			this.stationDao.insert(stationInfo);
		}
	}
	
}
