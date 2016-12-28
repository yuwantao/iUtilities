package com.java.miscellaneous;

import java.util.regex.Pattern;

/**
 * Created by yuwt on 2016/4/15.
 */
public class TestRegex {
	public static void main(String[] args) {
		String text = "http://bang.dangdang.com/books/bestsellers/01.00.00.00.00.00-24hours-0-0-1-1";
		String regex = "(-\\d+-\\d+-\\d+-)\\d+";
		Pattern p = Pattern.compile(regex);
		if (text.matches(regex)) {
			System.out.print("match");
		}


//		Matcher matcher = p.matcher(text);
//		while (matcher.find()) {
//			System.out.println(matcher.group());
//			System.out.println(matcher.group(1));
////			System.out.println(matcher.group(2));
////			System.out.println(matcher.group(3));
//		}
	}
}
