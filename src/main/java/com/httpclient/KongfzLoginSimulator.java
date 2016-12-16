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
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by yuwt on 2016/12/6.
 */
public class KongfzLoginSimulator {
	private static CloseableHttpClient client;

	static {
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
	}

	public static void main(String[] args) throws Exception {
//		test();
//		addToCartWithoutLogin();
//		addToCartWithLogin();
	}

	private static void addToCartWithLogin() throws Exception {
		login();
		addToCartWithoutLogin();
	}

	private static void addToCartWithoutLogin() throws Exception {
		String url = "http://search.kongfz.com/book/shopcart/add/";
		HttpPost post = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("itemId", "305553252"));
		params.add(new BasicNameValuePair("shopId", "7285"));
		params.add(new BasicNameValuePair("imgUrl", "G03/M00/F8/CA/pYYBAFTV30aAZNBRAAJ-2UmNkHk262.jpg"));
		params.add(new BasicNameValuePair("num", "1"));
		post.setEntity(new UrlEncodedFormEntity(params));
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		EntityUtils.consume(entity);
		System.out.println(content);
	}

	private static void test() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, IOException {


		login();

//		String keyword = "9787549569311";
//		String keyword = "红楼梦";
		String keyword = "0645757199579";
		String searchUrlPrefix = "http://search.kongfz.com/product/";
		String searchUrl = searchUrlPrefix + UnicodeUtils.convertToKongfzQueryString(keyword);
		HttpGet get = new HttpGet(searchUrl);
		CloseableHttpResponse response3 = client.execute(get);
		HttpEntity entity3 = response3.getEntity();
		String content3 = EntityUtils.toString(entity3);
		EntityUtils.consume(entity3);
//		System.out.println(content3);

		Document doc = Jsoup.parse(content3);
		String count = "0";
//		count = doc.select("div#b_c_nav").first().select("span.red1").text();
		Elements noRecord = doc.select("div.p_tb").select("span.red");
		System.out.println("no record:" + noRecord.size());
		System.out.println("count:" + count);

		Elements elements = doc.select("div.result_box.m_t10");
		System.out.println("current page count:" + elements.size());

		List<KongfzBookInfo> bookInfos = new ArrayList<>();

		elements.forEach(new Consumer<Element>() {
			@Override
			public void accept(Element element) {
				String bookId = element.attr("book-id");
				String shopId = element.attr("shop-id");
				String price = element.attr("price");
				String quality = element.select("p.m_t25").text();
//				System.out.println("book-id:" + bookId + "  shop-id:" + shopId + "  quality:" + quality);
				if ("十品".equals(quality)) {
					KongfzBookInfo bookInfo = new KongfzBookInfo();
					bookInfo.setBookId(bookId);
					bookInfo.setShopId(shopId);
					bookInfo.setPrice(new BigDecimal(price));
					bookInfos.add(bookInfo);
				}
			}
		});

		if (Integer.valueOf(count) > 50) {
			searchUrl += "w2";
			HttpGet _get = new HttpGet(searchUrl);
			CloseableHttpResponse _response3 = client.execute(_get);
			HttpEntity _entity3 = _response3.getEntity();
			String _content3 = EntityUtils.toString(_entity3);
			EntityUtils.consume(_entity3);
//		System.out.println(content3);

			Document _doc = Jsoup.parse(_content3);
//			String _count = doc.select("div#b_c_nav").first().select("span.red1").text();
//			System.out.println("count:" + count);

			Elements _elements = _doc.select("div.result_box.m_t10");
			System.out.println("second page count:" + _elements.size());

			_elements.forEach(new Consumer<Element>() {
				@Override
				public void accept(Element element) {
					String bookId = element.attr("book-id");
					String shopId = element.attr("shop-id");
					String price = element.attr("price");
					String quality = element.select("p.m_t25").text();
//				System.out.println("book-id:" + bookId + "  shop-id:" + shopId + "  quality:" + quality);
					if ("十品".equals(quality)) {
						KongfzBookInfo bookInfo = new KongfzBookInfo();
						bookInfo.setBookId(bookId);
						bookInfo.setShopId(shopId);
						bookInfo.setPrice(new BigDecimal(price));
						bookInfos.add(bookInfo);
					}
				}
			});
		}
		System.out.println("十品数目:" + bookInfos.size());
		Collections.sort(bookInfos);
		List<KongfzBookInfo> subList = bookInfos.subList(0, 5 > bookInfos.size() ? bookInfos.size() : 5);
		for (KongfzBookInfo b : subList) {
			System.out.println(b.getPrice());
		}
	}

	private static void login() throws IOException {
		String loginUrl = "https://login.kongfz.com/?returnUrl=http%3A%2F%2Fwww.kongfz.com%2F";
		String postUrl1 = "https://login.kongfz.com/index.php?m=Sign&c=Login&a=loginSign&returnUrl=http%3A%2F%2Fwww.kongfz.com%2F";
		String username = "yuwantao";
		String passwd = "xiaoyuer";

		HttpPost post = new HttpPost(postUrl1);
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("loginPass", passwd));
		post.setEntity(new UrlEncodedFormEntity(params));
		CloseableHttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		EntityUtils.consume(entity);
//		System.out.println(content);

		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(content);
		String sign = element.getAsJsonObject().get("data").getAsJsonObject().get("sign").getAsString();
//		System.out.println(sign);

		String postUrl2 = "https://login.kongfz.com/index.php?m=Sign&c=Login&a=loginAuth&returnUrl=http%3A%2F%2Fwww.kongfz.com%2F";
		HttpPost post2 = new HttpPost(postUrl2);
		List<NameValuePair> params2 = new ArrayList<>();
		params2.add(new BasicNameValuePair("autoLogin", "0"));
		params2.add(new BasicNameValuePair("loginName", username));
		params2.add(new BasicNameValuePair("loginPass", passwd));
		params2.add(new BasicNameValuePair("sign", sign));
		post2.setEntity(new UrlEncodedFormEntity(params2));
		CloseableHttpResponse response2 = client.execute(post2);
		HttpEntity entity2 = response2.getEntity();
		String content2 = EntityUtils.toString(entity2);
		EntityUtils.consume(entity2);
//		System.out.println(content2);
	}
}
