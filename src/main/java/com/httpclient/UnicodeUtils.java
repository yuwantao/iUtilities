package com.httpclient;

/**
 * Created by yuwt on 2016/12/6.
 */
public class UnicodeUtils {
	public static void main(String[] args) {
//		char c = '海';
////		System.out.println( "\\u" + Integer.toHexString(c | 0x10000).substring(1) );
//		System.out.println(charToUnicode(c));

		String str = "长征出版社";
		System.out.println(convertToKongfzQueryString(str));
	}

	public static String charToUnicode(char c) {
		String unicode = Integer.toHexString(c | 0x10000).substring(1);
		return unicode.replaceFirst("^0+(?!$)", "");
	}

	public static String convertToKongfzQueryString(String keyword) {
		String result = "z";
		for (char c : keyword.toCharArray()) {
			String s = charToUnicode(c);
			result = result + "k" + s;
		}
		return result;
	}
}
