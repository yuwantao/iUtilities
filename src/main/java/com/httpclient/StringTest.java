package com.httpclient;

/**
 * Created by yuwt on 2016/12/15.
 */
public class StringTest {
	public static void main(String[] args) {
		String str = "https://item.jd.com/10538945.html";
		String replace = str.replace("https://item.jd.com/", "").replace(".html", "");
		System.out.println(replace);

		String listPageUrlStub = "https://search.jd.com/Search?keyword=%s&enc=utf-8&qrst=1&rt=1&stop=1&book=y&vt=2&wtype=1";
		String format = String.format(listPageUrlStub, "123456");
		System.out.println(format);
	}
}
