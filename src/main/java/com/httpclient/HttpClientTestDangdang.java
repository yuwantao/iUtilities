package com.httpclient;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;

/**
 * Created by yuwt on 2016/12/15.
 */
public class HttpClientTestDangdang {
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

		String isbn = "9787100075039";
		String url = "http://search.dangdang.com/?key=" + isbn +
				"&act=input&filter=0%7C0%7C0%7C0%7C0%7C1%7C0%7C0%7C0%7C0%7C0%7C0%7C0%7C#J_tab";
//		String url = "https://item.jd.com/10538945.html";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response3 = null;
		try {
			response3 = client.execute(get);
			HttpEntity entity3 = response3.getEntity();
			String content3 = EntityUtils.toString(entity3, "utf-8");
			EntityUtils.consume(entity3);
//			System.out.println(content3);

			parseListPage(content3);
//			parseDetailPage(content3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseListPage(String content3) {
		Document doc = Jsoup.parse(content3);
		Elements elements = doc.select("span.search_now_price");
		if (elements.size() > 0) {
			String price = elements.first().text();
			System.out.println(price.trim().substring(1));
			Elements publishDateElements = doc.select("p.search_book_author").select("span");
			Iterator<Element> iterator = publishDateElements.iterator();
			iterator.next();
			Element next = iterator.next();
			String publishDate = next.text();
			System.out.println(publishDate.trim().substring(1));
		} else {
			Elements elements1 = doc.select("p.no_result_tips");
			if (elements1.size() > 0) {
				System.out.println(elements1.first().text());
			} else {
				//unknown
			}
		}
	}

}
