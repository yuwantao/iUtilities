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
public class HttpClientTestJd {
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
		String url = "http://dx.3.cn/desc/11687858?cdn=2&callback=showdesc";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response3 = null;
		try {
			response3 = client.execute(get);
			HttpEntity entity3 = response3.getEntity();
			String content = EntityUtils.toString(entity3, "utf-8");
			EntityUtils.consume(entity3);
//			System.out.println(content3);

			content = content.substring(9, content.length() - 1);

			JsonParser jsonParser = new JsonParser();
			JsonElement detailElement = jsonParser.parse(content);
			String detailContentText = detailElement.getAsJsonObject().get("content").getAsString();

			Document detailDoc = Jsoup.parse(detailContentText);
			Elements detailElements = detailDoc.select("div.book-detail-item");
			for (Element e : detailElements) {
				String text = e.text();
				if (text.equals("产品特色")) {
					System.out.println(e.select("div.book-detail-content").html());
				}
			}

//			parseListPage(content3);
//			parseDetailPage(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseDetailPage(String content) {
		String url = "https://p.3.cn/prices/get?skuid=J_11161337";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response3 = null;
		try {
			response3 = client.execute(get);
			HttpEntity entity3 = response3.getEntity();
			String content3 = EntityUtils.toString(entity3, "utf-8");
			EntityUtils.consume(entity3);
			System.out.println(content3);
//			System.out.println(content3.trim().substring(5, content3.length() -4));

			JsonParser jsonParser = new JsonParser();
			JsonElement element = jsonParser.parse(content3);
			String price = element.getAsJsonArray().get(0).getAsJsonObject().get("m").getAsString();
			System.out.println(price);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Document doc = Jsoup.parse(content);
		Elements publishDateElements = doc.select("ul#parameter2").select("li");
		Iterator<Element> iterator = publishDateElements.iterator();
		while (iterator.hasNext()) {
			Element next = iterator.next();
			if (next.text().contains("出版时间")) {
				String publishDate = next.attr("title");
				System.out.println(publishDate);
			}
		}
	}

	private static void parseListPage(String content3) {
		Document doc = Jsoup.parse(content3);
		Elements elements = doc.select("div.p-img");
		if (elements.size() > 0) {
			String detailPageUrl = elements.first().select("a").attr("href");
			System.out.println(detailPageUrl);
		} else {
			Elements elements1 = doc.select("div.ns-content, div.nf-content");
			if (elements1.size() > 0) {
				System.out.println(elements1.first().text());
			} else {
				//unknown
			}
		}
	}
}
