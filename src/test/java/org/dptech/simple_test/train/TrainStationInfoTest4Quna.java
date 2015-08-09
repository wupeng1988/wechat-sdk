package org.dptech.simple_test.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dptech.wap.train.persist.mapper.TrainConstants;
import org.dptech.wap.train.persist.model.TrainInfo;
import org.dptech.wap.train.persist.model.TrainPriceInfo;
import org.dptech.wap.train.util.DateUtil;
import org.dptech.wx.sdk.util.BeanUtil;
import org.dptech.wx.sdk.util.FileUtil;
import org.dptech.wx.sdk.util.HttpUtil;
import org.dptech.wx.sdk.util.JsonUtil;
import org.junit.Test;

public class TrainStationInfoTest4Quna {

	static Map<String, String> seat_type = new HashMap<>();
	
	static {
		seat_type.put("硬座", TrainConstants.SEAT_TYPE_YINGZUO);
		seat_type.put("硬卧", TrainConstants.SEAT_TYPE_YINGWO_XIA);
		seat_type.put("软卧", TrainConstants.SEAT_TYPE_RUANWO_XIA);
		seat_type.put("二等座", TrainConstants.SEAT_TYPE_ERDENGZUO);
		seat_type.put("一等座", TrainConstants.SEAT_TYPE_YIDENGZUO);
	}
	
	public static void main(String[] args) throws IOException {
		String json = HttpUtil.get("http://train.qunar.com/list_num.htm?fromStation=K52&date=2015-03-16", null);
		FileUtil.writeToFile("D:/1234.html", json, "UTF-8");
	}
	
	@Test
	public void test1() throws IOException{
//		test("Z318");
//		test("K52");
//		test("1461");
		test("K52");
//		test("G103");
	}
	
	public void test(String no) throws IOException{
//		String url = "http://train.qunar.com/list_num.htm?fromStation=z318&date=2015-03-16";
		String url = "http://train.qunar.com/qunar/checiInfo.jsp?"
				+ "method_name=buy"
				+ "&ex_track="
				+ "&q="+no
				+ "&date=" + DateUtil.format("yyyyMMdd", DateUtil.tomorrowDay())
				+ "&format=json"
				+ "&cityname=123456"
				+ "&ver=1426399687302"
				+ "&callback=XQScript_6";
		
		String json = HttpUtil.get(url, null);
		System.out.println(json);
		
		List<TrainPriceInfo> trainPriceInfo = new ArrayList<>();
		TrainInfo trainInfo = new TrainInfo();
		
		json = json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1);
		Map<String, Object> map = JsonUtil.toMap(json);
		Map<String, Object> ticketInfo = (Map<String, Object>) map.get("ticketInfo");
		List<Map<String, Object>> listMap = (List<Map<String, Object>>) ticketInfo.get(no);
		for(Map<String, Object> tmp : listMap){
			trainPriceInfo.add(new TrainPriceInfo(seat_type.get(tmp.get("type")), Double.valueOf(String.valueOf(tmp.get("pr")))));
		}
		
		TrainInfo4Quna qunaTrainInfo = BeanUtil.mapToBean((Map<String, Object>)((Map<String, Object>)map.get("trainInfo")).get(no), 
				TrainInfo4Quna.class);
		
		System.out.println(qunaTrainInfo.getArriCity_py());
		
		List<String> titles = (List<String>) map.get("trainScheduleHead");
		Map<Integer, String> attr_mapping = new HashMap<Integer, String>();
		
		for(int i = 0; i < titles.size(); i++){
			String title = titles.get(i);
			String attr = null;
			switch(title){
			case "站名" : {
				attr = "stationName";
				break;
			}
			case "日期" : {
				attr = "days";
				break;
			}
			case "到达时间" : {
				attr = "arriveTime";
				break;
			}
			case "开车时间" : {
				attr = "leaveTime";
				break;
			}
			case "停车时间" : {
				attr = "stayTime";
				break;
			}
			case "里程" : {
				attr = "distance";
				break;
			}
			case "硬座" : {
				attr = "hardSeatPrice";
				break;
			}
			case "硬卧下" : {
				attr = "underHardPrice";
				break;
			}
			case "软卧下" : {
				attr = "underSoftPrice";
				break;
			}
			case "一等座" : {
				attr = "firstClassSeatPrice";
				break;
			}
			case "二等座" : {
				attr = "secondClassSeatPrice";
				break;
			}
			default : 
				attr = null;
			}
			
			if(attr != null)
				attr_mapping.put(i, attr);
		}
		
		List<Map<String, Object>> trainStationInfos = (List<Map<String, Object>>) map.get("trainScheduleBody");
		List<TrainStationInfo4Quna> entitys = new ArrayList<>();
		for(Map<String, Object> trainStationInfo : trainStationInfos){
			if(trainStationInfo == null)
				continue;
			
			Map<String, String> entityAttrs = new HashMap<String, String>(); 
			List<String> contents = (List<String>) trainStationInfo.get("content");
			for(int i = 0; i < contents.size(); i++){
				String attrName = attr_mapping.get(i);
				if(attrName == null){
					continue;
				}
				entityAttrs.put(attrName, contents.get(i));
			}
			
			TrainStationInfo4Quna entity = BeanUtil.mapToBean(map, TrainStationInfo4Quna.class);
			if(entity != null) {
				entitys.add(entity);
			}
		}
		
		System.out.println(entitys.size());
	}
	
	
	public static class TrainStationInfo4Quna {
		String stationName;//站名
		String days;//天数
		String arriveTime;//到达时间
		String leaveTime;//离开时间
		String stayTime;//停留时间
		String distance;//距离
		String hardSeatPrice;//硬座价格
		String underHardPrice;//硬卧下价格
		String underSoftPrice;//软卧下价格
		String secondClassSeatPrice;//二等座价格
		String firstClassSeatPrice;//一等座价格
		
		public String getStationName() {
			return stationName;
		}
		public void setStationName(String stationName) {
			this.stationName = stationName;
		}
		public String getDays() {
			return days;
		}
		public void setDays(String days) {
			this.days = days;
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
		public String getStayTime() {
			return stayTime;
		}
		public void setStayTime(String stayTime) {
			this.stayTime = stayTime;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		public String getHardSeatPrice() {
			return hardSeatPrice;
		}
		public void setHardSeatPrice(String hardSeatPrice) {
			this.hardSeatPrice = hardSeatPrice;
		}
		public String getUnderHardPrice() {
			return underHardPrice;
		}
		public void setUnderHardPrice(String underHardPrice) {
			this.underHardPrice = underHardPrice;
		}
		public String getUnderSoftPrice() {
			return underSoftPrice;
		}
		public void setUnderSoftPrice(String underSoftPrice) {
			this.underSoftPrice = underSoftPrice;
		}
		public String getSecondClassSeatPrice() {
			return secondClassSeatPrice;
		}
		public void setSecondClassSeatPrice(String secondClassSeatPrice) {
			this.secondClassSeatPrice = secondClassSeatPrice;
		}
		public String getFirstClassSeatPrice() {
			return firstClassSeatPrice;
		}
		public void setFirstClassSeatPrice(String firstClassSeatPrice) {
			this.firstClassSeatPrice = firstClassSeatPrice;
		}
		
	}
	
	public static class TrainPriceInfo4Quna {
		double pr;
		String type;
		public double getPr() {
			return pr;
		}
		public void setPr(double pr) {
			this.pr = pr;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
	
	public static class TrainInfo4Quna {
		String arriCity_py;//到达城市 拼音（beijing）
		String dptDate;//查询日期 2015-03-16
		String code;//车次 K52
		String status;//状态 1
		String arriTime;//到达时间（05：02）
		boolean istmp;//是否临时 false
		String deptCity_py;//始发站城市 拼音 （rizhao）
		String arrDate;//到达日期（2015-03-17）
		String arriCity;//到达城市，（北京）
		String trainType;//列车类型(空调快速)
		String deptTime;//始发站发车时间(17:05)
		String arriStation;//到达车站(北京)
		String deptStation;//始发车站（日照）
		String deptCity;//始发城市(日照)
		String interval;//列车运行间隔(12小时15分钟)
		
		public String getArriCity_py() {
			return arriCity_py;
		}
		public void setArriCity_py(String arriCity_py) {
			this.arriCity_py = arriCity_py;
		}
		public String getDptDate() {
			return dptDate;
		}
		public void setDptDate(String dptDate) {
			this.dptDate = dptDate;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getArriTime() {
			return arriTime;
		}
		public void setArriTime(String arriTime) {
			this.arriTime = arriTime;
		}
		public boolean isIstmp() {
			return istmp;
		}
		public void setIstmp(boolean istmp) {
			this.istmp = istmp;
		}
		public String getDeptCity_py() {
			return deptCity_py;
		}
		public void setDeptCity_py(String deptCity_py) {
			this.deptCity_py = deptCity_py;
		}
		public String getArrDate() {
			return arrDate;
		}
		public void setArrDate(String arrDate) {
			this.arrDate = arrDate;
		}
		public String getArriCity() {
			return arriCity;
		}
		public void setArriCity(String arriCity) {
			this.arriCity = arriCity;
		}
		public String getTrainType() {
			return trainType;
		}
		public void setTrainType(String trainType) {
			this.trainType = trainType;
		}
		public String getDeptTime() {
			return deptTime;
		}
		public void setDeptTime(String deptTime) {
			this.deptTime = deptTime;
		}
		public String getArriStation() {
			return arriStation;
		}
		public void setArriStation(String arriStation) {
			this.arriStation = arriStation;
		}
		public String getDeptStation() {
			return deptStation;
		}
		public void setDeptStation(String deptStation) {
			this.deptStation = deptStation;
		}
		public String getDeptCity() {
			return deptCity;
		}
		public void setDeptCity(String deptCity) {
			this.deptCity = deptCity;
		}
		public String getInterval() {
			return interval;
		}
		public void setInterval(String interval) {
			this.interval = interval;
		}
		
		
	}
	
	public static class TrainExtInfo {
		String allMileage;
		String allTime;
		public String getAllMileage() {
			return allMileage;
		}
		public void setAllMileage(String allMileage) {
			this.allMileage = allMileage;
		}
		public String getAllTime() {
			return allTime;
		}
		public void setAllTime(String allTime) {
			this.allTime = allTime;
		}
	}
	
}
