package org.dptech.simple_test.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.dptech.wap.train.persist.mapper.TrainConstants;
import org.dptech.wap.train.persist.model.TrainInfo;
import org.dptech.wap.train.persist.model.TrainPriceInfo;
import org.dptech.wap.train.persist.model.TrainStationLink;
import org.dptech.wap.train.persist.model.TrainStationPriceInfo;
import org.dptech.wx.sdk.util.BeanUtil;
import org.dptech.wx.sdk.util.HttpUtil;
import org.dptech.wx.sdk.util.StringUtil;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 
 * 从火车票网站抓取的  http://www.huochepiao.net/checi_Z318
 * 
 * @author wupeng
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:applicationContext.xml")
public class TrainStationInfo4Huochepiao {
	
	static Map<String, String> train_info_mapping = new HashMap<>();
	
	static {
		train_info_mapping.put("车次", "trainNo");
		train_info_mapping.put("全程运行", "totalTime");
		train_info_mapping.put("始发站", "startStationCode");
		train_info_mapping.put("终点站", "endStationCode");
		train_info_mapping.put("发车时间", "startTime");
		train_info_mapping.put("到站时间", "endTime");
		train_info_mapping.put("类型", "type");
		train_info_mapping.put("全程", "totalDistance");
	}
	
	
	
	@Test
	public void getTrainStation() throws IOException, ParserException, OgnlException{
		String queryTrainNo = "G69";
		String url = "http://www.huochepiao.net/checi_" + queryTrainNo;
		String json = HttpUtil.get(url, null, "GBK");
		
		TagNameFilter tableFilter = new TagNameFilter("table");
		HasAttributeFilter bgcolorFilter = new HasAttributeFilter("bgcolor", "#0033cc");
		AndFilter andFilter = new AndFilter(tableFilter, bgcolorFilter);
		
		Parser parser = Parser.createParser(json, "GBK");
		
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		if(nodeList.size() == 2){
			TrainInfo train = this.parseTrainInfo(nodeList.elementAt(0));
			if(train != null && train.getPrices() != null){
				for(TrainPriceInfo priceInfo : train.getPrices()){
					if(priceInfo != null){
						priceInfo.setTrainNo(queryTrainNo);
					}
				}
			}
			
			System.out.println(train);
			
			List<TrainStationLink> trainStations = this.parseTrainStationInfo(nodeList.elementAt(1));
			System.out.println(trainStations.size());
		}
	}
	
	
	public List<TrainStationLink> parseTrainStationInfo(Node trainStationNode){
		List<TrainStationLink> result = new ArrayList<TrainStationLink>();
		List<WrappedTrainStationLink> wrappedStation = new ArrayList<>();
		
		NodeList trs = trainStationNode.getChildren();
		Map<Integer, String> attr_mapping = new HashMap<Integer, String>();
		int rowCount = 0;
		for(int i = 0; i < trs.size(); i++){
			Node tr = trs.elementAt(i);
			
			if(!(tr instanceof TableRow)){
				continue;
			}
			
			rowCount++;
			
			TableRow row = (TableRow) tr;
			
			if(rowCount == 1){
				attr_mapping = this.indexAttributeMapping(row);
				continue;
			}
			
			TableColumn[] tds = row.getColumns();
			Map<String, String> attrValues = new HashMap<>();
			for(int j = 0; j < tds.length; j++){
				String attrName = attr_mapping.get(j);
				if(attrName == null){
					continue;
				}
				
				Node child = tds[j].getFirstChild();
				if(child == null){
					continue;
				}
				
				if(child instanceof LinkTag){
					attrValues.put(attrName, child.getFirstChild().getText());
				} else {
					attrValues.put(attrName, child.getText());
				}
				
			}
			
			wrappedStation.add(BeanUtil.mapToBean(attrValues, WrappedTrainStationLink.class));
		}
		
		for(WrappedTrainStationLink link : wrappedStation){
			result.add(this.convertToEntity(link));
		}
		
		return result;
	}
	
	private TrainStationLink convertToEntity(WrappedTrainStationLink link){
		
		String code = getStationCodeByName(link.getStationName());
		
		TrainStationLink t = new TrainStationLink();
		t.setArriveTime(link.getArriveTime());
		t.setDays(link.getDays());
		t.setDistance(link.getDistance());
		t.setLeaveTime(link.getLeaveTime());
		t.setRunTime(link.getRunTime());
		t.setStationName(link.getStationName());
		t.setStationOrder(link.getStationOrder());
		t.setStayTime(link.getStayTime());
		t.setTrainNo(link.getTrainNo());
		
		String trainNo = t.getTrainNo().toLowerCase();
		boolean isHighSpeedRail = false;
		if(trainNo.startsWith("d") || trainNo.startsWith("g")){
			isHighSpeedRail = true;
		}
		
		String tmpPrice = link.getHardSeatPrice();//硬座 / 二等座
		if(StringUtil.isDigit(tmpPrice)){
			Double price = Double.valueOf(tmpPrice);
			if(isHighSpeedRail){
				t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_ERDENGZUO, price));
			} else {
				t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_YINGZUO, price));
			}
		}
		
		tmpPrice = link.getSoftSeatPrice();
		if(StringUtil.isDigit(tmpPrice)){//软座 / 一等座
			Double price = Double.valueOf(tmpPrice);
			if(isHighSpeedRail){
				t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_YIDENGZUO, price));
			} else {
				t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_RUANZUO, price));
			}
		}
		
		tmpPrice = link.getHardSleeperPrice();// 硬卧上/中/下
		if(!StringUtils.isEmpty(tmpPrice)){
			String[] prices = tmpPrice.split("/");
			if(prices.length == 3){
				if(StringUtil.isDigit(prices[0])){
					t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_YINGWO_SHANG, Double.valueOf(prices[0])));
				}
				
				if(StringUtil.isDigit(prices[1])){
					t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_YINGWO_ZHONG, Double.valueOf(prices[1])));
				}
				
				if(StringUtil.isDigit(prices[2])){
					t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_YINGWO_XIA, Double.valueOf(prices[2])));
				}
			}
		}
		
		tmpPrice = link.getSoftSleeperPrice();
		if(!StringUtils.isEmpty(tmpPrice)){// 软卧 上/下
			String[] prices = tmpPrice.split("/");
			if(prices.length == 2){
				if(StringUtil.isDigit(prices[0])){
					t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_RUANWO_SHANG, Double.valueOf(prices[0])));
				}
				
				if(StringUtil.isDigit(prices[1])){
					t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_RUANWO_XIA, Double.valueOf(prices[1])));
				}
			}
		}
		
//		tmpPrice = link.getBusinessSeatPrice();
//		if(!StringUtils.isEmpty(tmpPrice)){// 特等/商务座
//			String[] prices = tmpPrice.split("/");
//			if(StringUtil.isDigit(prices[0])){
//				t.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_TEDENG, Double.valueOf(prices[0])));
//			}
//			
//			if(StringUtil.isDigit(prices[1])){
//				t.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_SHANGWUZUO, Double.valueOf(prices[1])));
//			}
//		}
		tmpPrice = link.getBusinessSeatPrice();
		if(StringUtil.isDigit(tmpPrice)){// 特等/商务座
			t.addPrice(new TrainStationPriceInfo(t.getTrainNo(), code, TrainConstants.SEAT_TYPE_TEDENG, Double.valueOf(tmpPrice)));
		}
		
		return t;
	}
	
	/**
	 * 
	 * 标题行 每一列对应的哪个属性
	 * 
	 * @param titleTr
	 * @return
	 */
	private Map<Integer, String> indexAttributeMapping(TableRow titleRow){
		Map<Integer, String> map = new HashMap<Integer, String>();
		TableColumn[] columns = titleRow.getColumns();
		for(int i = 0; i < columns.length; i++){
			TableColumn column = columns[i];
			String title = column.getFirstChild().getText();
			String attr = null;
			switch(title){
			case "车次" : {
				attr = "trainNo";
				break;
			}
			case "站次" : {
				attr = "stationOrder";
				break;
			}
			case "站名" : {
				attr = "stationName";
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
			case "运行时间" : {
				attr = "runTime";
				break;
			}
			case "天数" : {
				attr = "days";
				break;
			}
			case "里程" : {
				attr = "distance";
				break;
			}
			case "硬座价" : {
				attr = "hardSeatPrice";
				break;
			}
			case "软座价" : {
				attr = "softSeatPrice";
				break;
			}
			case "硬卧上/中/下" : {
				attr = "hardSleeperPrice";
				break;
			}
			case "软卧上/下" : {
				attr = "softSleeperPrice";
				break;
			}
			case "特等/商务座" : {
				attr = "businessSeatPrice";
				break;
			}
			default : 
				attr = null;
			}
			
			if(attr != null){
				map.put(i, attr);
			}
		}
		
		return map;
	}
	
	/**
	 * 
	 * 解析火车信息
	 * 
	 * @param trainInfoNode
	 * @return
	 * @throws OgnlException
	 */
	public TrainInfo parseTrainInfo(Node trainInfoNode) throws OgnlException{
		TrainInfo trainInfo = new TrainInfo();
		
		NodeList trs = trainInfoNode.getChildren();
		
		for(int i = 0; i < trs.size(); i++){
			Node tr = trs.elementAt(i);
			if(!(tr instanceof TableRow)){
				continue;
			}
			
			TableRow row = (TableRow) tr;
			TableColumn[] cols = row.getColumns();
			if(cols == null || cols.length <= 2){
				continue;
			}
			
			int colCount = cols.length;
			
			for(int j = 0; j < colCount; j++){
				TableColumn col = cols[j];
				if(!StringUtils.isEmpty(col.getAttribute("rowspan"))){
					continue;
				}
				if("4".equals(col.getAttribute("colspan")) && !col.getFirstChild().toHtml().contains("</a>")){
					trainInfo.setSummery(col.getFirstChild().getText());//eg.北京西-广州南(当天到)
				}
				
				TableColumn next = null;
				if(j + 1 < colCount){
					next = cols[j + 1];
				}
				
				String colName = col.getFirstChild().getText();
				switch(colName){
				case "车次" : {
					String no = next.getFirstChild().getFirstChild().getText();
					trainInfo.setTrainNo(no);
					break;
				}
				case "硬座" : {
					String price = next.getFirstChild().getText();
					if(StringUtil.isDigit(price)){
						trainInfo.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_YINGZUO, Double.valueOf(price)));
					}
					break;
				}
				case "软座" : {
					String price = next.getFirstChild().getText();
					if(StringUtil.isDigit(price)){
						trainInfo.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_RUANZUO, Double.valueOf(price)));
					}
					break;
				}
				case "硬卧上/中/下" : {
					String[] prices = next.getFirstChild().getText().split("/");
					String price = prices[0];
					if(StringUtil.isDigit(price)){
						trainInfo.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_YINGWO_SHANG, Double.valueOf(price)));
					}
					price = prices[1];
					if(StringUtil.isDigit(price)){
						trainInfo.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_YINGWO_ZHONG, Double.valueOf(price)));
					}
					price = prices[2];
					if(StringUtil.isDigit(price)){
						trainInfo.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_YINGWO_XIA, Double.valueOf(price)));
					}
					break;
				}
				case "软卧上/下" : {
					String[] prices = next.getFirstChild().getText().split("/");
					String price = prices[0];
					if(StringUtil.isDigit(price)){
						trainInfo.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_RUANWO_SHANG, Double.valueOf(price)));
					}
					price = prices[1];
					if(StringUtil.isDigit(price)){
						trainInfo.addPrice(new TrainPriceInfo(TrainConstants.SEAT_TYPE_RUANWO_XIA, Double.valueOf(price)));
					}
					break;
				}
				
				default : {
					String attrName = train_info_mapping.get(colName);
					if(!StringUtils.isEmpty(attrName)){
						String attrVal = next.getFirstChild().getText();
						Ognl.setValue(attrName, trainInfo, attrVal);
					}
				}
				}
			}
		}
		
		return trainInfo;
	}
	
	
	public String getStationCodeByName(String stationName) {
		return stationName;
	}
	
	
	public static class WrappedTrainStationLink extends TrainStationLink {
		String hardSeatPrice;//硬座价格
		String softSeatPrice;//软座价格
		String hardSleeperPrice;//卧铺价格
		String softSleeperPrice;//软卧价格
		String businessSeatPrice;//特等商务价格
		
		public String getHardSeatPrice() {
			return hardSeatPrice;
		}
		public void setHardSeatPrice(String hardSeatPrice) {
			this.hardSeatPrice = hardSeatPrice;
		}
		public String getSoftSeatPrice() {
			return softSeatPrice;
		}
		public void setSoftSeatPrice(String softSeatPrice) {
			this.softSeatPrice = softSeatPrice;
		}
		public String getHardSleeperPrice() {
			return hardSleeperPrice;
		}
		public void setHardSleeperPrice(String hardSleeperPrice) {
			this.hardSleeperPrice = hardSleeperPrice;
		}
		public String getSoftSleeperPrice() {
			return softSleeperPrice;
		}
		public void setSoftSleeperPrice(String softSleeperPrice) {
			this.softSleeperPrice = softSleeperPrice;
		}
		public String getBusinessSeatPrice() {
			return businessSeatPrice;
		}
		public void setBusinessSeatPrice(String businessSeatPrice) {
			this.businessSeatPrice = businessSeatPrice;
		}
		
	}
	
	
}
