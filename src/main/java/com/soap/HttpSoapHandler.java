package com.soap;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by yuwt on 2016/12/14.
 */
public class HttpSoapHandler {
	private static CloseableHttpClient client;

	static {
		RequestConfig reqConfig = RequestConfig.custom()
				.setConnectTimeout(10 * 1000)
				.setSocketTimeout(10 * 1000)
				.build();

		client = HttpClients.custom()
				.setDefaultRequestConfig(reqConfig)
				.build();
	}

	public static void send() {
//		String url = "http://192.168.2.100:85/cipupdate.asmx";
		String url = "http://192.168.2.155:8008/cipupdate.asmx";
		HttpPost post = new HttpPost(url);
		String noid = "2706317";
		String price = "38.40";
		String chubanshijian = "";
		String msg = SoapMessageGenerator.generate(noid, price, chubanshijian);
//		System.out.println(msg);
		ContentType contentType = ContentType.create("text/xml", Charset.forName("utf-8"));
		StringEntity stringEntity = new StringEntity(msg, contentType);
		post.setEntity(stringEntity);
		post.setHeader("SOAPAction", "http://tempuri.org/exbook_cipupdate");

		try {
			CloseableHttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			response.close();
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		HttpSoapHandler.send();
	}
}
