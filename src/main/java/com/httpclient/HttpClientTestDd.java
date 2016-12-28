package com.httpclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuwt on 2016/12/28.
 */
public class HttpClientTestDd {
	private static CloseableHttpClient client;

	public static void main(String[] args) {
		SSLConnectionSocketFactory sslSocketFactory;

		SSLContext sslctx = null;
		try {
			sslctx = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
		} catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
			e.printStackTrace();
		}
		sslSocketFactory = new SSLConnectionSocketFactory(sslctx);
		BasicCookieStore cookieStore = new BasicCookieStore();

		RequestConfig requestConfig = RequestConfig.custom()
				//.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				//.setProxy(new HttpHost("127.0.0.1", 8888))
				.setConnectTimeout(30*1000)
				.setSocketTimeout(30*1000)
				.build();

		client = HttpClients.custom()
				.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
				.setSSLSocketFactory(sslSocketFactory)
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore)
				.build();

//		String url = "https://search.jd.com/Search?keyword=9787535353536&enc=utf-8&qrst=1&rt=1&stop=1&book=y&vt=2&wtype=1";
		String url = "http://product.dangdang.com/23439259.html";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response3 = null;

		try {
			response3 = client.execute(get);
			HttpEntity entity3 = response3.getEntity();
			String content = EntityUtils.toString(entity3, "utf-8");
			EntityUtils.consume(entity3);

			String regex = "var\\s+prodSpuInfo\\s+=\\s+(.+?});";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(content);
			if (m.find()) {
				String productInfo = m.group(1);
				System.out.println(productInfo);
				JsonParser jsonParser = new JsonParser();
				JsonElement element = jsonParser.parse(productInfo);
				String categoryPath = element.getAsJsonObject().get("categoryPath").getAsString();
				String productId = element.getAsJsonObject().get("productId").getAsString();
				String shopId = element.getAsJsonObject().get("shopId").getAsString();
				System.out.println(categoryPath);
				System.out.println(productId);
				System.out.println(shopId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
