package com.httpclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuwt on 2016/12/30.
 */
public class OpenbookSpider {
	private static final CloseableHttpClient client;

	static {
		//ssl
		SSLContext sslCtx = null;
		try {
			sslCtx = new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true).build();
		} catch (Exception ignored) {
		}
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslCtx);

		//cookie store
		BasicCookieStore cookieStore = new BasicCookieStore();

		PoolingHttpClientConnectionManager proxyConnMgr = new PoolingHttpClientConnectionManager();
		proxyConnMgr.setMaxTotal(2000);
		proxyConnMgr.setDefaultMaxPerRoute(200);
		//evit expired and idle connections periodically

		client = HttpClients.custom()
				.setConnectionManager(proxyConnMgr)
				.setRetryHandler(new DefaultHttpRequestRetryHandler(3, false))
				.setSSLSocketFactory(sslSocketFactory)
				.setDefaultCookieStore(cookieStore)
				.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
				.build();
	}

	public static void main(String[] args) throws Exception {
		String username = "liujingchao@readgo.cn";
		String passwd = "1234qwer";
//		login(username, passwd);

		parseListPage();
	}

	private static void parseListPage() throws Exception{
		String origin_isbn = "9787504763426";
//		String origin_isbn = "9787304083458";
		String url = "http://www.openbookdata.com.cn/Templates/BookListTemplate.tt?_ver=636045374486309073&_orderindex=0&_ordertype=0&__mode=1&_pageIndex=0&_pageSize=10&showtype=1&title=" + origin_isbn +
				"&author=&pubname=&plotname=&publishsdate=&publishedate=&selShip=-1&starttime=&endtime=&chkispub=0&chkisrecommend=0&selkind=&kinds=&pd=&st=";
		CloseableHttpResponse response = client.execute(new HttpGet(url));
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		EntityUtils.consume(entity);
		response.close();
//		System.out.println(content);

		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(content);
		String count = element.getAsJsonObject().get("cnt").getAsString();
		String message = element.getAsJsonObject().get("message").getAsString();
		JsonObject object = element.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject();
		String isbn = object.get("ISBN").getAsString();
		String title = object.get("Title").getAsString();
		String price = object.get("Price").getAsString();
		String author = object.get("Author").getAsString();
		String publishDate = object.get("PublishDate").getAsString();

		System.out.println(message);
	}

	public static void login(String username, String password) throws Exception {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ln", username));
		params.add(new BasicNameValuePair("pw", password));

		HttpPost loginPost = new HttpPost("http://www.openbookdata.com.cn/handlers/UserController/UserLogins.ashx");
		loginPost.setEntity(new UrlEncodedFormEntity(params));
		HttpResponse response = client.execute(loginPost);
		String result = EntityUtils.toString(response.getEntity());
		result = result.replaceAll("\"|\'|\\s", "");
		if(result.contains("userstate:1")) {
			System.out.println("成功登录，开卷网站");
		} else {
			System.out.println("登录开卷网站失败：" + result);
			throw new Exception("登录开卷网站失败：" + result);
		}

	}
}
