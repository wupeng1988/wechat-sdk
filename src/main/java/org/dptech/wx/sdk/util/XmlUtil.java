package org.dptech.wx.sdk.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Jaxb2工具类
 * 
 */
public class XmlUtil {

	/**
	 * xml转化为Map
	 * @param xml
	 * @return
	 */
	public static Map<String, String> convertToMap(String xml){
		try {
			Map<String, String> map = new HashMap<String, String>();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element xmlEle = doc.getDocumentElement();
			NodeList nList = xmlEle.getChildNodes();
			for(int i = 0; i< nList.getLength() ; i ++){
				Element node = (Element)nList.item(i);
				map.put(node.getTagName(), node.getTextContent());  
			}
			
            return map;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;
	}
	
	/**
	 * JavaBean转换成xml
	 * 默认编码UTF-8
	 * @param obj
	 * @param writer
	 * @return 
	 * @throws JAXBException 
	 */
	public static String convertToXml(Object obj) throws JAXBException {
		return convertToXml(obj, "UTF-8");
	}

	/**
	 * JavaBean转换成xml
	 * @param obj
	 * @param encoding 
	 * @return 
	 * @throws JAXBException 
	 */
	public static String convertToXml(Object obj, String encoding) throws JAXBException {
		String result = null;
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		result = writer.toString();

		return result;
	}
	
	/**
	 * xml转换成JavaBean
	 * @param xml
	 * @param c
	 * @return
	 * @throws JAXBException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(String xml, Class<T> c) throws JAXBException {
		T t = null;
		JAXBContext context = JAXBContext.newInstance(c);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		t = (T) unmarshaller.unmarshal(new StringReader(xml));

		return t;
	}
	
	/**
	 * 去掉标准的xml最上面那一行声明:<?xml version="1.0" encoding="UTF-8" ?>
	 * 
	 * @param xml
	 * @return
	 */
	public static String removeDeclare(String xml){
		int index1 = xml.indexOf("<?xml");
		int index2 = xml.lastIndexOf("?>");
		
		if(index1 >= 0 && index2 >= 0){
			xml = xml.substring(index2 + 2);
			xml = xml.trim();
		}
		
		return xml;
	}
}