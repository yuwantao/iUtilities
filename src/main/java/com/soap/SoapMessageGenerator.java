package com.soap;

/**
 * Created by yuwt on 2016/12/14.
 */
public class SoapMessageGenerator {
	private static final String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
			"  <soap:Body>\n" +
			"    <exbook_cipupdate xmlns=\"http://tempuri.org/\">\n" +
			"      <noid>noid_placeholder</noid>\n" +
			"      <price>price_placeholder</price>\n" +
			"      <chubanshijian>chubanshijian_placeholder</chubanshijian>\n" +
			"    </exbook_cipupdate>\n" +
			"  </soap:Body>\n" +
			"</soap:Envelope>";

	public static String generate(String noid, String price, String chubanshijian) {
		return msg.replace("noid_placeholder", noid)
				.replace("price_placeholder", price)
				.replace("chubanshijian_placeholder", chubanshijian);
	}
}
