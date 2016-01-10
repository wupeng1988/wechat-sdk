package org.dptech.simple_test.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dptech.wap.train.persist.dao.TrainStationInfoDao;
import org.dptech.wap.train.persist.model.TrainStationInfo;
import org.dptech.wx.sdk.util.FileUtil;
import org.dptech.wx.sdk.util.HttpUtil;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
public class AllTrainStation {
	
	@Autowired
	TrainStationInfoDao stationDao;
	
	@Test
	@Rollback(false)
	public void allStations() throws IOException, ParserException{
		String url = "http://www.59178.com/Zhan/";
		String json = HttpUtil.get(url, null, "GBK");
		FileUtil.writeToFile("D:/1234.html", json, "GBK");
		
		TagNameFilter aTag = new TagNameFilter("a");
		HasParentFilter tdParent = new HasParentFilter(new AndFilter(
				new TagNameFilter("td"), new HasAttributeFilter("width", "20%")));
		
		AndFilter andFilter = new AndFilter(aTag, tdParent);
		
		Parser parser = Parser.createParser(json, "GBK");
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < nodeList.size(); i++){
			Node node = nodeList.elementAt(i);
			String code = null;
			String name = null;
			
			if(node instanceof Tag){
				Tag tag = (Tag) node;
				code = tag.getAttribute("href");
				if(!StringUtils.isEmpty(code)){
					code = code.replace("/zhan/", "").replace(".htm", "");
				}
			} else {
				code = node.toHtml();
				code = code.replace("<a href=\"/zhan/", "");
				code = code.substring(0, code.indexOf(".htm"));
			}
			
			name = node.getFirstChild().getText();
			list.add("code:" + code + ",name:" + name);
			
			TrainStationInfo info = new TrainStationInfo();
			info.setCode(code);
			info.setName(name);
			info.setUpdateTimestamp(System.currentTimeMillis());
			stationDao.insert(info);
		}
		
		FileUtil.writeToFile("D:/1234.json", list);
		
	}
	
}
