package com.httpclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by yuwt on 2016/12/6.
 */
public class KongfzLoginSimulator {
	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException {
		SSLConnectionSocketFactory sslSocketFactory;

		SSLContext sslctx = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();
		sslSocketFactory = new SSLConnectionSocketFactory(sslctx);

		BasicCookieStore cookieStore = new BasicCookieStore();

		RequestConfig requestConfig = RequestConfig.custom()
				//.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				//.setProxy(new HttpHost("127.0.0.1", 8888))
				.setConnectTimeout(30*1000)
				.setSocketTimeout(30*1000)
				.build();

		CloseableHttpClient client = HttpClients.custom()
				.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
				.setSSLSocketFactory(sslSocketFactory)
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore)
				.build();

		String loginUrl = "https://login.kongfz.com/?returnUrl=http%3A%2F%2Fwww.kongfz.com%2F";
		String postUrl1 = "https://login.kongfz.com/index.php?m=Sign&c=Login&a=loginSign&returnUrl=http%3A%2F%2Fwww.kongfz.com%2F";
		String username = "yuwantao";
		String password = "xiaoyuer";

		HttpPost post = new HttpPost(postUrl1);
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("loginPass", password));
		post.setEntity(new UrlEncodedFormEntity(params));
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		EntityUtils.consume(entity);
		System.out.println(content);

		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(content);
		String sign = element.getAsJsonObject().get("data").getAsJsonObject().get("sign").getAsString();
		System.out.println(sign);

		String postUrl2 = "https://login.kongfz.com/index.php?m=Sign&c=Login&a=loginAuth&returnUrl=http%3A%2F%2Fwww.kongfz.com%2F";
		HttpPost post2 = new HttpPost(postUrl2);
		List<NameValuePair> params2 = new ArrayList<>();
		params2.add(new BasicNameValuePair("autoLogin", "0"));
		params2.add(new BasicNameValuePair("loginName", username));
		params2.add(new BasicNameValuePair("loginPass", password));
		params2.add(new BasicNameValuePair("sign", sign));
		post2.setEntity(new UrlEncodedFormEntity(params2));
		CloseableHttpResponse response2 = client.execute(post2);
		HttpEntity entity2 = response2.getEntity();
		String content2 = EntityUtils.toString(entity2);
		EntityUtils.consume(entity2);
		System.out.println(content2);

		String keyword = "9787549569311";
		String searchUrlPrefix = "http://search.kongfz.com/product/";
		String searchUrl = searchUrlPrefix + UnicodeUtils.convertToKongfzQueryString(keyword);
		HttpGet get = new HttpGet(searchUrl);
		CloseableHttpResponse response3 = client.execute(get);
		HttpEntity entity3 = response3.getEntity();
		String content3 = EntityUtils.toString(entity3);
		EntityUtils.consume(entity3);
//		System.out.println(content3);

		Document doc = Jsoup.parse(content3);
		Elements elements = doc.select("div.result_box.m_t10");
		System.out.println("size:" + elements.size());
		elements.forEach(new Consumer<Element>() {
			@Override
			public void accept(Element element) {
				String bookId = element.attr("book-id");
				String shopId = element.attr("shop-id");
				System.out.println("book-id:" + bookId + "  shop-id:" + shopId);
			}
		});
	}
}
