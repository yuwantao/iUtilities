package com.xml;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.jaxen.SimpleNamespaceContext;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuwt on 2016/11/10.
 */
public class Dom4jTest {
	private static final Logger log = Logger.getLogger(Dom4jTest.class);

	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File("e:\\download\\test.xml"));
			Element rootElement = doc.getRootElement();
			Namespace namespace = rootElement.getNamespace();
			String ns = namespace.getStringValue();


			HashMap uris = new HashMap();
			uris.put("r", ns);
			XPath xPath = doc.createXPath("//r:table1_CONTENT_NAME");
			xPath.setNamespaceContext(new SimpleNamespaceContext(uris));
			List table1ContentNameList = xPath.selectNodes(doc);
//			Node node = (Node)table1ContentName.get(0);
//			XPath xPath1 = node.createXPath("r:CONTENT_NAME1/@CONTENT_NAME1");
//			xPath1.setNamespaceContext(new SimpleNamespaceContext(uris));
//			Node node1 = xPath1.selectSingleNode(node);
//			Node node1 = node.selectSingleNode("CONTENT_NAME1");
//			String stringValue = node.selectSingleNode("CONTENT_NAME1/@CONTENT_NAME1").getStringValue();

			Element element = (Element) table1ContentNameList.get(0);
			Attribute content_name1 = element.element("CONTENT_NAME1").attribute("CONTENT_NAME1");
			log.info(content_name1.getStringValue());
			List group3List = element.element("Group3_Collection").elements("Group3");
			for (Object o : group3List) {
				Element group3 = (Element) o;
				if (group3.attribute("Group3").getStringValue().contains("multiuser")) {
					List group2List = group3.element("Group2_Collection").elements("Group2");
					for (Object o1 : group2List) {
						Element group2 = (Element) o1;
						String issueDateStr = group2.attribute("Group2").getStringValue();
						log.info(issueDateStr);
						Date date = DateUtils.parseDate(issueDateStr, new String[]{"yyyy-MM-dd'T'hh:mm:ss"});
						log.info("util:" + date);
						log.info("sql:" + new java.sql.Date(date.getTime()));
						log.info("sql timestamp:" + new Timestamp(date.getTime()));
						List detailList = group2.element("Detail_Collection").elements("Detail");
						for (Object o2 : detailList) {
							Element detail = (Element) o2;
							String purchaseDateStr = detail.attribute("The_Date").getStringValue();
							String partnerName = detail.attribute("PARTNER_NAME").getStringValue();
							String deviceCategory = detail.attribute("Device_Category").getStringValue();
							String deviceType = detail.attribute("Device_Type").getStringValue();
							String pricePerCopy = detail.attribute("PricePerCopy").getStringValue();
							String country = detail.attribute("Country").getStringValue();
							String printed = detail.attribute("Printed").getStringValue();

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
