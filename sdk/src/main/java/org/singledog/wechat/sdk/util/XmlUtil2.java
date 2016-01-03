package org.singledog.wechat.sdk.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class XmlUtil2 {
	
	private static final Logger logger = LoggerFactory.getLogger(XmlUtil2.class);
	private static SAXReader reader = new SAXReader();
	private static final Lock lock = new ReentrantLock();
	
	public static synchronized Map<String, String> toMap(String xml){
		Map<String, String> map = new HashMap<>();
		Document doc = null; 
		lock.lock();
		try {
			doc = reader.read(new StringReader(xml));//StringReader 无需关闭  @see StringReader.close
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.unlock();
		}
		
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> elements = root.elements();
		for(Element e : elements){
			map.put(e.getName(), e.getText());
		}
		
		return map;
	}
}