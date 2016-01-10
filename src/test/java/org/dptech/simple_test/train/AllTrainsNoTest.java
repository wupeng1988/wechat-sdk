package org.dptech.simple_test.train;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import org.dptech.wap.train.persist.dao.TrainInfoDao;
import org.dptech.wap.train.spider.TrainInfoSpiderDispatcher;
import org.dptech.wx.sdk.util.HttpUtil;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * 获取所有车次名
 * 
 * @author wupeng
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
//@ContextConfiguration("classpath:applicationContext.xml")
public class AllTrainsNoTest {

	private static final Logger logger = LoggerFactory.getLogger(AllTrainsNoTest.class);
	
//	@Autowired
	static TrainInfoSpiderDispatcher dispatcher;
	
	static TrainInfoDao trainInfoDao;
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		dispatcher = context.getBean(TrainInfoSpiderDispatcher.class);
		trainInfoDao = context.getBean(TrainInfoDao.class);
		
		try {
			new AllTrainsNoTest().test();
		} catch (ParserException | IOException | InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	@Test
//	@Transactional
//	@Rollback(false)
	public void test() throws IOException, ParserException, InterruptedException{
		String url = "http://www.zd9999.com/cc/";
		String json = HttpUtil.get(url, null, "GBK");
		
		File file = new File("D:/1234.html");
		OutputStream os = new FileOutputStream(file);
		os.write(json.getBytes("GBK"));
		os.flush();
		os.close();
		logger.error("ok");
		long start = System.currentTimeMillis();
		
		Parser parser = Parser.createParser(json, "GBK");
		TagNameFilter afilter = new TagNameFilter("a");
		HasParentFilter parentFilter = new HasParentFilter(new AndFilter(new TagNameFilter("td"), 
						new HasAttributeFilter("width", "20%")));
		
		AndFilter andFilter = new AndFilter(new NodeFilter[]{afilter, parentFilter});
		
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		
		
		BufferedWriter out = new BufferedWriter(new FileWriter("F:/12306/all_train_no2.json"));
		
		if(nodeList != null){
			logger.error(nodeList.size()+"");
			for(int i = 0; i < nodeList.size(); i++){
				Node link = nodeList.elementAt(i);
				String text = link.getFirstChild().getText();
				if(text.contains("/")){
					String[] nos = text.split("/");
					for(String no : nos){
						out.write(no);
						out.newLine();
						out.flush();
						if(trainInfoDao.getByTrainNo(text) == null)
							this.merge(no);
					}
				} else {
					out.write(text);
					out.newLine();
					out.flush();
					if(trainInfoDao.getByTrainNo(text) == null)
						this.merge(text);
				}
				
				if(i % 1000 == 0){
//					TimeUnit.SECONDS.sleep(5);
				}
			}
		}
		
		out.flush();
		out.close();
		
//		System.out.println(System.currentTimeMillis() - start);
		
	}
	
	private void merge(String trainNo) throws InterruptedException{
		if(!StringUtils.isEmpty(trainNo)){
			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fetch train no : " + trainNo);
			this.dispatcher.persistCompositeTrainStationInfo(this.dispatcher.extractTrainStationInfo(trainNo));
//			TimeUnit.SECONDS.sleep(2);
		}
	}
	
}